package com.biedronka.biedronka_recipes.service;

import com.biedronka.biedronka_recipes.dto.CookActionResponseDTO;
import com.biedronka.biedronka_recipes.entity.*;
import com.biedronka.biedronka_recipes.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CookingService {

    private final ClientRepository clientRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeProductRepository recipeProductRepository; // do pobierania potrzebnych składników
    private final ProductClientRepository productClientRepository;  // do sprawdzania stanu spiżarni
    private final ShoppingListRepository shoppingListRepository; // do aktualizacji listy zakupów (opcjonalnie)
    private final ClientRecipeLikeRepository clientRecipeLikeRepository;
    private final CommentRepository commentRepository;
    private final CommentRecipeRepository commentRecipeRepository;
    private final OpinionRepository opinionRepository;
    private final OpinionRecipeRepository opinionRecipeRepository;

    public CookingService(ClientRepository clientRepository,
                          RecipeRepository recipeRepository,
                          RecipeProductRepository recipeProductRepository,
                          ProductClientRepository productClientRepository,
                          ShoppingListRepository shoppingListRepository,
                          ClientRecipeLikeRepository clientRecipeLikeRepository,
                          CommentRepository commentRepository,
                          CommentRecipeRepository commentRecipeRepository,
                          OpinionRepository opinionRepository,
                          OpinionRecipeRepository opinionRecipeRepository) {
        this.clientRepository = clientRepository;
        this.recipeRepository = recipeRepository;
        this.recipeProductRepository = recipeProductRepository;
        this.productClientRepository = productClientRepository;
        this.shoppingListRepository = shoppingListRepository;
        this.clientRecipeLikeRepository = clientRecipeLikeRepository;
        this.commentRepository = commentRepository;
        this.commentRecipeRepository = commentRecipeRepository;
        this.opinionRepository = opinionRepository;
        this.opinionRecipeRepository = opinionRecipeRepository;
    }

    // ------------------------
    // 1) GŁÓWNA LOGIKA: GOTUJ
    // ------------------------
    public CookActionResponseDTO handleExecuteRecipe(Long clientId, Long recipeId) {
        // Krok 1. (już za nami – klient wybrał przepis z listy)
        // Krok 2. (w praktyce mamy go w bazie, "udostępniamy" w interfejsie)

        // weryfikacja
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        // Krok 5. POBIERAMY *składniki* potrzebne do przepisu

        List<RecipeProduct> neededProducts = recipeProductRepository.findByRecipeId(recipeId);

        // Krok 6. Sprawdzamy, czy klient ma wszystkie składniki
        HashMap<Product,Integer> missing = new HashMap<>();

        for (RecipeProduct rp : neededProducts) {
            Product product = rp.getProduct();
            Double neededAmount = rp.getAmount();


            Optional<ProductClient> pcOpt = productClientRepository.findByProductIdAndClientId(clientId,product.getId());


            if (pcOpt.isPresent()) {
                ProductClient pc = pcOpt.get();
                if (pc.getAmount() < neededAmount) {
                    double diff =  (neededAmount - pc.getAmount());
                    double weight = product.getWeight();
                    int shouldBuyQuantity = (int) Math.ceil(diff / weight);


                    // Za mało – brak
                    missing.put(product,shouldBuyQuantity);
                }
            } else {
                // Brak w spiżarni w ogóle
                double weight = product.getWeight();
                int shouldBuyQuantity = (int) Math.ceil(neededAmount / weight);
                missing.put(product,shouldBuyQuantity);
            }
        }

        // Krok 7. Dwie sytuacje:
        if (!missing.isEmpty()) {
            // SCENARIUSZ A / A’
            // – A1.7 => potwierdzamy brak składników
            // System informuje o braku
            //  - A3 => Klient rezygnuje (przebieg A) => partial success
            //  - A’ => Klient “mimo to” wykonuje przepis => do listy zakupów

            // W realu spytalibyśmy front: “Czy chcesz wykonać MIMO braków (A’)?”
            // Załóżmy, że default -> rezygnuje (A - partial success).

            // Dla uproszczenia automatycznie realizujemy “A – rezygnacja, plus dodanie do listy zakupów”
            //   LUB realizujemy “A’ – “mimo to”, co prowadzi do “FAIL”.

            // Tutaj przyjmuję, że skoro brakuje, to wykonujemy scenariusz A - rezygnacja
            // Zaktualizujmy listę zakupów
            for (Map.Entry<Product, Integer> entry : missing.entrySet()) {
                Product product = entry.getKey();        // Klucz (product ID)
                Integer quantity = entry.getValue(); // Wartość (brakująca ilość)

                // Dodaj do listy zakupów (ShoppingListItem)
                ShoppingList sl = new ShoppingList();
                sl.setClient(client);         // Ustaw klienta
                sl.setProduct(product);      // Przypisz ID produktu
                sl.setQuantity(quantity);     // Ustaw brakującą ilość

                shoppingListRepository.save(sl);
            }

            // Zwracamy informację, że brakuje składników, rezygnujemy
            return new CookActionResponseDTO(false,
                    "Brakuje składników. Zaktualizowano listę zakupów. Rezygnacja z wykonania przepisu.",
                    missing);
        }

        // => JEST OK, NIC nie brakuje
        // Krok 8. Klient potwierdza wykonanie przepisu –
        // tu zakładamy, że samo wywołanie handleExecuteRecipe oznacza “potwierdził”.

        // Krok 9. Aktualizujemy spiżarnię (odejmujemy składniki)
        for (RecipeProduct rp : neededProducts) {
            Double neededAmount = rp.getAmount();
            Product product = rp.getProduct();
            // Znajdź w productClient i odejmij
            ProductClient pc = productClientRepository.findAll().stream()
                    .filter(x -> x.getClient().getId().equals(clientId)
                            && x.getProduct().getId().equals(product.getId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("W bazie brak ProductClient?"));

            pc.setAmount(pc.getAmount() - neededAmount);
            productClientRepository.save(pc);
        }

        // Krok 10. Sukces
        return new CookActionResponseDTO(true, "Przepis wykonany pomyślnie!", null);
    }

    // ------------------------
    // 2) POZOSTAŁE OPCJE
    // ------------------------

    // PRZEBIEG D: LIKE
    public CookActionResponseDTO handleLikeRecipe(Long clientId, Long recipeId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        ClientRecipeLikes like = new ClientRecipeLikes();
        like.setClient(client);
        like.setRecipe(recipe);
        clientRecipeLikeRepository.save(like);

        // D4. Goto 3 => w realu wracamy do wyboru kolejnej akcji
        return new CookActionResponseDTO(true, "Przepis polubiony!", null);
    }

    // PRZEBIEG B: COMMENT
    public CookActionResponseDTO handleCommentRecipe(Long clientId, Long recipeId, String content) {
        if (content == null || content.isBlank()) {
            return new CookActionResponseDTO(false, "Pusty komentarz", null);
        }
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        // Tworzymy komentarz
        Comment c = new Comment();
        c.setContent(content);
        c.setClient(client);
        c = commentRepository.save(c);

        // Tworzymy bridging: CommentRecipe
        CommentRecipe cr = new CommentRecipe();
        cr.setComment(c);
        cr.setRecipe(recipe);
        cr.setDate(java.time.LocalDate.now());
        commentRecipeRepository.save(cr);

        return new CookActionResponseDTO(true, "Dodano komentarz do przepisu.", null);
    }

    // PRZEBIEG C: OPINION
    public CookActionResponseDTO handleOpinionRecipe(Long clientId, Long recipeId, Integer stars) {
        if (stars == null || stars < 1 || stars > 5) {
            return new CookActionResponseDTO(false, "Ocena musi być w skali 1–5", null);
        }
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        // Tworzymy Opinion
        Opinion o = new Opinion();
        o.setStars(stars);
        o.setClient(client);
        o = opinionRepository.save(o);

        // Bridging
        OpinionRecipe or = new OpinionRecipe();
        or.setOpinion(o);
        or.setRecipe(recipe);
        or.setDate(java.time.LocalDate.now());
        opinionRecipeRepository.save(or);

        // C3. System aktualizuje ocenę przepisu – w prostym wariancie
        //     (np. brak osobnego pola w Recipe,
        //      ewentualnie liczymy średnią ze wszystkich OpinionRecipe?)

        return new CookActionResponseDTO(true, "Opinia zapisana!", null);
    }

    // PRZEBIEG E: BACK
    public CookActionResponseDTO handleBackToList() {
        // E1.4 – “Klient wybiera opcję wróć”
        // E2. Kończy się niepowodzeniem, bo wracamy do listy i nie wykonaliśmy przepisu
        return new CookActionResponseDTO(false, "Powrót do listy przepisów", null);
    }
}
