const selectedTags = new Set();
const selectedRatings = new Set();

function toggleFilters() {
    const filters = document.getElementById('filters');
    filters.classList.toggle('open');
}

function toggleTagSelection(tagElement) {
    const tagId = tagElement.getAttribute('data-id'); // Get the tag ID from the `data-id` attribute

    if (selectedTags.has(tagId)) {
        selectedTags.delete(tagId); // Remove tag ID from the set if already selected
        tagElement.classList.remove('selected'); // Remove the selected class for styling
    } else {
        selectedTags.add(tagId); // Add tag ID to the set if not selected
        tagElement.classList.add('selected'); // Add the selected class for styling
    }
}

function toggleRatingSelection(ratingElement, rating) {
    if (selectedRatings.has(rating)) {
        selectedRatings.delete(rating);
        ratingElement.classList.remove('selected');
    } else {
        selectedRatings.add(rating);
        ratingElement.classList.add('selected');
    }
}

function searchRecipes() {
    const keyword = document.getElementById('searchKeyword').value;
    const tags = Array.from(selectedTags);
    const ratings = Array.from(selectedRatings);
    const filterByAllergens = document.getElementById('filterByClientAllergens').checked;

    fetch(`/search/matchRecipes?keyword=${keyword}&tags=${tags.join(',')}&ratings=${ratings.join(',')}&filterByAllergens=${filterByAllergens}`)
        .then(response => response.json())
        .then(recipes => {
            console.log('Recipes from backend:', recipes); // Debug log

            const resultsContainer = document.getElementById('recipeResults');
            resultsContainer.innerHTML = '';

            if (recipes.length === 0) {
                resultsContainer.innerHTML = `<p class="no-results">No recipes found. Try another search.</p>`;
            } else {
                recipes.forEach(recipe => {
                    const recipeHtml = `
                    <div class="recipe">
                        <img src="${recipe.multimediaUrl}" alt="${recipe.name}">
                        <h4>${recipe.name}</h4>
                        <p>${recipe.description}</p>
                        <p><strong>Created by:</strong> ${recipe.employeeName}</p>
                        <p><strong>Tags:</strong> ${recipe.tags.join(', ')}</p>
                    </div>
                `;
                    resultsContainer.innerHTML += recipeHtml;
                });
            }
        })
        .catch(error => {
            console.error('Error fetching recipes:', error);
        });
}
