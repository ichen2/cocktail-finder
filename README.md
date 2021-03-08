# cocktail-finder

#### find your new favorite cocktail!

CocktailFinder lets you search for cocktails (using the first letter of your desired drink) and displays them in a list.
Currently, only the cocktail name and image are displayed, but I plan to add ingredients, instructions, types, and more.

## implementation

CocktailFinder is based on TheCocktailDB's API, which allows you to retrieve lists of cocktails.
I used Retrofit to call the API and get the data, and Gson to deserialize it.
The app uses an MVVM architecture to pass this data to the Activity.
I also used Glide to load the cocktail images.
