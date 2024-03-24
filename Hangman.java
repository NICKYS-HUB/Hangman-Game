import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Hangman {
    
    //constants for different difficulty levels
    private static final int EASY = 0;
    private static final int MEDIUM = 1;
    private static final int HARD = 2;
    private static final int RANDOM = 3; //only applicaple for RandoMania
    
    //arrays for the different available categories
    private static final int NUMBER_OF_CATEGORIES = 4;
    private static final int WORDS_PER_DIFFICULTY = 20;
    private static final int NUMBER_OF_DIFFICULTIES = 3;
    
    private static String[][] animals = new String[NUMBER_OF_DIFFICULTIES][WORDS_PER_DIFFICULTY];
    private static String[][] movies = new String[NUMBER_OF_DIFFICULTIES][WORDS_PER_DIFFICULTY];
    private static String[][] countries = new String[NUMBER_OF_DIFFICULTIES][WORDS_PER_DIFFICULTY];
    private static String[][] idioms = new String[NUMBER_OF_DIFFICULTIES][WORDS_PER_DIFFICULTY];
    
    
    //general game play variables
    private static int itemsGuessed = 0;
    private static int attemptCount = 0;
    private static int score = 0;
    
    private static HashMap<String, Integer> classicScores = new HashMap<String, Integer>();
    private static HashMap<String, Integer> phraseScores = new HashMap<String, Integer>();
    private static HashMap<String, Integer> randoManiaScores = new HashMap<String, Integer>();
    
    private static String hiddenItem;
    private static String randomCategoryChosen;
    
    
    public static void main(String[] args) {
        
        //creating the words for the different categories
        createInitialGameItems();
        
        //declare objects
        Scanner input = new Scanner(System.in);
        
        //declare variables
        String playerChoice;
        boolean gameLoopOn = true;
        
        System.out.println("--------------- Welcome to Hangman ---------------");
        
        //Main menu
        while (gameLoopOn) {
            System.out.println("--------------- Main Menu ---------------");
            System.out.println("Type \"stop program\" at any time to quit.");
            System.out.println("\n" + "Please select a game mode:");
            System.out.println("1. Classic");
            System.out.println("2. Phrase");
            System.out.println("3. RandoMania");
            System.out.println("4. Scores");
            
            //player input
            System.out.print("Input: ");
            playerChoice = input.nextLine();
            
            //ending program
            if (playerChoice.equalsIgnoreCase("stop program"))
                terminateProgram();
            
            //calling appropriate function based off player choice
            if (playerChoice.equals("1") || playerChoice.equalsIgnoreCase("Classic"))
                startClassicGameMode();
            else if (playerChoice.equals("2") || playerChoice.equalsIgnoreCase("Phrase"))
                startPhraseGameMode();
            else if (playerChoice.equals("3") || playerChoice.equalsIgnoreCase("RandoMania"))
                startRandoManiaMode();
            else if (playerChoice.equals("4") || playerChoice.equalsIgnoreCase("Score"))
                startScoreMode();
            else {
                System.out.println("Please enter an appropriate input.");
                continue;
            }
        }
        
    }
    
    // Add a new score for a player
    public static void addScore(String gameMode, int score) {
        
        //declaring objects
        Scanner input = new Scanner(System.in);
        
        //player input
        System.out.println("------------- Add name to score board -------------");
        System.out.print("Enter Name: ");
        String playerName = input.nextLine();
        
        //terminating program if player chooses to do so
        if (playerName.equalsIgnoreCase("stop program"))
            terminateProgram();
            
        //adding score to appropriate HashMap
        switch (gameMode) {
            case "Classic": classicScores.put(playerName, score); break;
            case "Phrase": phraseScores.put(playerName, score); break;
            case "RandoMania": randoManiaScores.put(playerName, score); break;
        }
        
    }
    
    
    //calculates the player's score after winning a round
    public static int calculateScore(int itemsGuessed, int attemptCount) {
        attemptCount = 6 - attemptCount;
        int score = (100 * itemsGuessed) + ( (20 * attemptCount) - 20 );
        return score;
    }
    
    
    //creating of words for different categories
    public static void createInitialGameItems() {
        /* creating words for different playerChoice levels and storing them 
        in arrays to be added to animals array*/
        String[] easyAnimalWords = {"Bear", "Butterfly","Cheetah", "Crocodile",
                                    "Dolphin", "Eagle", "Elephant", "Giraffe",
                                    "Hippo", "Hyena", "Kangaroo", "Koala",
                                    "Lion", "Octopus","Owl", "Panda", "Penguin",
                                    "Snake", "Tiger", "Zebra"};
        
        String[] mediumAnimalWords = {"Aardvark", "Butterfly","Antelope", "Armadillo",
                                    "Blobfish", "Capybara", "Coati", "Civet",
                                    "Cuckoo", "Fossa", "Hartebeest", "Honeycreeper",
                                    "Iguana", "Manatee","Narwhal", "Numbat", "Ocelot",
                                    "Okapi", "Shrew", "Whipbird"};
        
        String[] hardAnimalWords = {"Chevrotain", "Curassow","Echidna", "Gaur",
                                    "Guineafowl", "Jerboa", "Kakapo", "Kipunji",
                                    "Kinkajou", "Langur", "Loach", "Murrelet",
                                    "Pangolin", "Planigale","Quokka", "Saola", "Solenodon",
                                    "Tarpan", "Tarsier", "Vaquita"};
        
        //adding arrays to animals array depending on difficullty
        animals[EASY] = easyAnimalWords;
        animals[MEDIUM] = mediumAnimalWords;
        animals[HARD] = hardAnimalWords;
        
        
        /* creating words for different playerChoice levels and storing them 
        in arrays to be added to countries array*/
        String[] easyCountryNames = {"Argentina", "Australia","Brazil", "Canada",
                                    "China", "Egypt", "France", "Germany",
                                    "India", "Italy", "Japan", "Mexico",
                                    "Nigeria", "Russia","Saudi Arabia", "South Africa",
                                    "South Korea", "Spain", "United Kingdom", "United States of America"};
        
        String[] mediumCountryNames = {"Armenia", "Azerbaijan","Belarus", "Bolivia",
                                    "Cameroon", "Chad", "Cyprus", "Estonia",
                                    "Finland", "Georgia", "Guinea", "Kazakhstan",
                                    "Philippines", "Latvia", "Lithuania", "Moldova",
                                    "Mongolia", "Paraguay", "Tajikistan", "Turkmenistan"};
        
        String[] hardCountryNames = {"Andorra", "Bhutan", "Comoros", "Djibouti",
                                    "Eswatini", "Guyana", "Kiribati", "Kyrgyzstan",
                                    "Liechtenstein", "Luxembourg", "Monaco", "Nauru",
                                    "Palau", "San Marino", "Seychelles", "Suriname",
                                    "Timor-Leste", "Tuvalu", "Vanuatu", "Micronesia"};
        
        //adding arrays to countries array depending on difficullty
        countries[EASY] = easyCountryNames;
        countries[MEDIUM] = mediumCountryNames;
        countries[HARD] = hardCountryNames;
        
        
        /* creating words for different playerChoice levels and storing them 
        in arrays to be added to countries array*/
        String[] easyMovieNames = {"The Shawshank Redemption", "The Godfather", "Pulp Fiction", "The Dark Knight",
                                    "Schindler's List", "Forrest Gump", "Titanic", "Avatar",
                                    "The Matrix", "Star Wars", "Jurassic Park", "Gone with the Wind",
                                    "Casablanca", "Inception", "The Silence of the Lambs", "The Sound of Music",
                                    "The Avengers", "Iron Man", "Black Panther", "The Justice League"};
        
        String[] mediumMovieNames = {"A Ghost Story", "A Monster Calls", "Brick", "Coherence",
                                    "Eternal Sunshine of the Spotless Mind", "Hunt for the Wilderpeople",
                                    "In Bruges", "Moon", "Nightcrawler", "Pan's Labyrinth",
                                    "Predestination", "Primer", "Safety Not Guaranteed", "Sing Street",
                                    "SnowPiercer", " The Fall", "The Grand Budapest Hotel",
                                    "The Nice Guys", "The Prestige", "Wind River"};
        
        String[] hardMovieNames = {"After Hours", "Bronson", "Cache", "Fish Tank",
                                    "Ghost World", "In the Mood for Love", "Kedi", "Memories of Murder",
                                    "Mystery Men", "Paths of Glory", "Persepolis", "Run Lola Run",
                                    "Samsara", "Song of the Sea", "The Counterfeiters", "The Hunt",
                                    "The Lobster", "The Secret in Their Eyes", "The Station Agent", "The Man from Earth"};
        
        //adding arrays to countries array depending on difficullty
        movies[EASY] = easyMovieNames;
        movies[MEDIUM] = mediumMovieNames;
        movies[HARD] = hardMovieNames;
        
        String[] easyIdioms = {
                                "A piece of cake",
                                "Break a leg",
                                "Bite the bullet",
                                "Cost an arm and a leg",
                                "Don't cry over spilled milk",
                                "Don't put all your eggs in one basket",
                                "Kick the bucket",
                                "Hit the nail on the head",
                                "The ball is in your court",
                                "When pigs fly",
                                "Actions speak louder than words",
                                "Burning the midnight oil",
                                "A penny for your thoughts",
                                "Don't judge a book by its cover",
                                "Every cloud has a silver lining",
                                "Once in a blue moon",
                                "Break the ice",
                                "The early bird catches the worm",
                                "Hitting the hay",
                                "Take it with a grain of salt"
                            };
        
        String[] mediumIdioms = {
                                    "Burning the candle at both ends",
                                    "Beggars can't be choosers",
                                    "Jumping on the bandwagon",
                                    "Don't count your chickens before they hatch",
                                    "Put all your eggs in one basket",
                                    "The pot calling the kettle black",
                                    "Swinging for the fences",
                                    "Hitting the nail on the head",
                                    "You can't judge a book by its cover",
                                    "A watched pot never boils",
                                    "In the heat of the moment",
                                    "Don't throw the baby out with the bathwater",
                                    "Bite off more than you can chew",
                                    "Dressed to the nines",
                                    "Caught between a rock and a hard place",
                                    "A leopard can't change its spots",
                                    "A wolf in sheep's clothing",
                                    "A storm in a teacup",
                                    "The devil is in the details",
                                    "The proof is in the pudding"
                                };
        
        String[] hardIdioms = {
                                    "As fit as a fiddle",
                                    "A nest of vipers",
                                    "As busy as a one-armed paperhanger",
                                    "A pennyweight more or less makes no difference",
                                    "Carry coals to Newcastle",
                                    "To buy a cat in a sack",
                                    "The proof of the pudding is in the eating",
                                    "To have two strings to one's bow",
                                    "As daft as a brush",
                                    "To have a finger in every pie",
                                    "To know which side one's bread is buttered on",
                                    "To carry fire in one hand and water in the other",
                                    "To wash one's dirty linen in public",
                                    "A bridge too far",
                                    "To throw a spanner in the works",
                                    "To roll out the red carpet",
                                    "To take the wind out of someone's sails",
                                    "To talk turkey",
                                    "To turn over a new leaf",
                                    "To speak of the devil"
                                };
        
        
        idioms[EASY] = easyIdioms;
        idioms[MEDIUM] = mediumIdioms;
        idioms[HARD] = hardIdioms;
        
        classicScores.put("Nicole", calculateScore(1,4));
        classicScores.put("Muelelwa", calculateScore(1,5));
        classicScores.put("Lebo", calculateScore(1,5));
        classicScores.put("Lona", calculateScore(1,5));
        classicScores.put("Hlulani", calculateScore(1,5));
        
        phraseScores.put("Lona", calculateScore(1,4));
        phraseScores.put("Lebo", calculateScore(1,5));
        phraseScores.put("Muelelwa", calculateScore(1,5));
        phraseScores.put("Nicole", calculateScore(1,5));
        phraseScores.put("Hlulani", calculateScore(1,5));
        
        randoManiaScores.put("Lebo", calculateScore(1,4));
        randoManiaScores.put("Nicole", calculateScore(1,5));
        randoManiaScores.put("Lona", calculateScore(1,5));
        randoManiaScores.put("Muelelwa", calculateScore(1,5));
        randoManiaScores.put("Hlulani", calculateScore(1,5));
        
    }
    
    
    //returns either "Easy", "Medium", or "Hard" depending on the difficultyIndex
    public static String getDifficultyString(int difficultyIndex) {
        String difficulty = "";
        
        //setting difficulty to either 
        switch (difficultyIndex) {
            case EASY:
                difficulty = "Easy";
                break;
            case MEDIUM:
                difficulty = "Medium";
                break;
            case HARD:
                difficulty = "Hard";
                break;
            case RANDOM:
                difficulty = "Random";
                break;
        }
        
        return difficulty;
    }
    
    
    //method to select an item from the animal category
    public static String getItemToBeGuessed(String category, int difficulty) {
        
        //declaring variables
        Random random = new Random();
        String item = "";
        
        //array for the different categories in case of category = random
        String[] categories = {"Animals", "Countries", "Movies", "Idioms"};
        
        if (category.equals("Random")) {
            category = categories[random.nextInt(NUMBER_OF_CATEGORIES)];
            randomCategoryChosen = category;
        }
        switch (category) {
            case "Animals":
                //saving animal to item depending on playerChoice
                switch(difficulty) {
                    case EASY:
                        item = animals[EASY][random.nextInt(WORDS_PER_DIFFICULTY)];
                        break;
                    case MEDIUM:
                        item = animals[MEDIUM][random.nextInt(WORDS_PER_DIFFICULTY)];
                        break;
                    case HARD:
                        item = animals[HARD][random.nextInt(WORDS_PER_DIFFICULTY)];
                        break;
                    case RANDOM:
                        item = animals[random.nextInt(NUMBER_OF_DIFFICULTIES)][random.nextInt(WORDS_PER_DIFFICULTY)];
                        break;
                }
                break;
                
            case "Countries":
                //saving country to item depending on playerChoice
                switch(difficulty) {
                    case EASY:
                        item = countries[EASY][random.nextInt(WORDS_PER_DIFFICULTY)];
                        break;
                    case MEDIUM:
                        item = countries[MEDIUM][random.nextInt(WORDS_PER_DIFFICULTY)];
                        break;
                    case HARD:
                        item = countries[HARD][random.nextInt(WORDS_PER_DIFFICULTY)];
                        break;
                    case RANDOM:
                        item = countries[random.nextInt(NUMBER_OF_DIFFICULTIES)][random.nextInt(WORDS_PER_DIFFICULTY)];
                        break;
                }
                break;
                
            case "Movies":
                //saving movie to item depending on playerChoice
                switch(difficulty) {
                    case EASY:
                        item = movies[EASY][random.nextInt(WORDS_PER_DIFFICULTY)];
                        break;
                    case MEDIUM:
                        item = movies[MEDIUM][random.nextInt(WORDS_PER_DIFFICULTY)];
                        break;
                    case HARD:
                        item = movies[HARD][random.nextInt(WORDS_PER_DIFFICULTY)];
                        break;
                    case RANDOM:
                        item = movies[random.nextInt(NUMBER_OF_DIFFICULTIES)][random.nextInt(WORDS_PER_DIFFICULTY)];
                        break;
                }
                break;
                
            case "Idioms":
                //saving idiom to item depending on playerChoice
                switch(difficulty) {
                    case EASY:
                        item = idioms[EASY][random.nextInt(WORDS_PER_DIFFICULTY)];
                        break;
                    case MEDIUM:
                        item = idioms[MEDIUM][random.nextInt(WORDS_PER_DIFFICULTY)];
                        break;
                    case HARD:
                        item = idioms[HARD][random.nextInt(WORDS_PER_DIFFICULTY)];
                        break;
                    case RANDOM:
                        item = idioms[random.nextInt(NUMBER_OF_DIFFICULTIES)][random.nextInt(WORDS_PER_DIFFICULTY)];
                        break;
                }
                break;
        }
        
        //returning animal word
        return item.toUpperCase();
    }
    
    
    //allows player to enter input for their guess
    public static void getPlayerGuesses(List<Character>playerGuesses) {
        //declaring objects
        Scanner input = new Scanner(System.in);
        String itemGuess = "";
        
        
        //try-catch block to handle error in case player presses enter without entering value
        try {
            
            //input
            System.out.print("Enter a letter: ");
        
            itemGuess = input.nextLine().toUpperCase();
            if (itemGuess.equalsIgnoreCase("stop program"))
            terminateProgram();
        
            //adding player guess to list
            playerGuesses.add(itemGuess.charAt(0));

            if (!hiddenItem.contains(itemGuess))
                attemptCount++;
            
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Please enter a valid input.");
        }
        
    }
    
    
    //checking if score is eligble to be added to high scores
    public static boolean isScoreEligibleForHighScores(int score) {
        //declaring variables
        boolean isEligible = false;
        HashMap<String, Integer> scoreMap = classicScores;
        
        //going through high score list to check if the current score is greater than any of existing scores
        for (Map.Entry<String, Integer> entry : scoreMap.entrySet()) {
            
            isEligible = score >= entry.getValue();
            
            //breaking loop if current score is higher than a score on the list
            if (isEligible)
                break;
        }
        
        
        return isEligible;
    }
    
    
    //print the hidden word. letters that haven't been guessed yet are replaced with dashes
    public static boolean printCurrentWordState(List<Character> playerGuesses) {
        
        int correctCount = 0;
        
        //increment correctCount if there is a symbol in the word/letter/phrase
        if (playerGuesses.contains('-'))
            correctCount++;
        if (playerGuesses.contains('\''))
            correctCount++;
        if (playerGuesses.contains(':'))
            correctCount++;
        
        //for-loop to print word
        for (int i = 0; i < hiddenItem.length(); i++) {
            
            //print itemGuess if playerGuesses contains word
            if (playerGuesses.contains(hiddenItem.charAt(i)) &&
                !(hiddenItem.charAt(i) == '-') &&
                !(hiddenItem.charAt(i) == '\'') &&
                !(hiddenItem.charAt(i) == ':')) {
                System.out.print(" " + hiddenItem.charAt(i));
                correctCount++;
            
             //if current iteration of hiddenItem is a space
            } else if (hiddenItem.charAt(i) == ' ') {
                System.out.print("  ");
                
             //if current iteration of hiddenItem is an apostrephe
            } else if ( hiddenItem.charAt(i) == '\'' &&
                        playerGuesses.contains(hiddenItem.charAt(i - 1) ) &&
                        playerGuesses.contains(hiddenItem.charAt(i + 1) )) {
                System.out.print(" '");
                
             //if current iteration of hiddenItem is a dash
            } else if ( hiddenItem.charAt(i) == '-' &&
                        playerGuesses.contains(hiddenItem.charAt(i - 1) ) &&
                        playerGuesses.contains(hiddenItem.charAt(i + 1) )) {
                System.out.print(" -");
                
             //if current iteration of hiddenItem is a colon
            } else if ( hiddenItem.charAt(i) == ':' &&
                        playerGuesses.contains(hiddenItem.charAt(i - 1) ) ) {
                System.out.print(" :");
                
             //if playerGuesses does not contain a itemGuess in hiddenItem, print dash
            } else {
                System.out.print(" _");
            }
        }
        System.out.println();
        
        //return true if player has guessed the word
        return (hiddenItem.length() == correctCount);
    }
    
    
    // print hangman figure
    public static void printHangedMan() {
        
        //declaring variables
        String head = "";
        String leftArm = "";
        String torso = "";
        String rightArm = "";
        String leftLeg = "";
        String rightLeg = "";
        
        if (attemptCount >= 1)
            head = "O";
        if (attemptCount >= 2)
            leftArm = "/";
        if (attemptCount >= 3)
            torso = "|";
        if (attemptCount >= 4)
            rightArm = "\\";
        if (attemptCount >= 5)
            leftLeg = "/";
        if (attemptCount >= 6)
            rightLeg = " \\";

        System.out.println("  ||===========|_|");
        System.out.println("  ||            |");
        System.out.println("  ||            " + head);
        System.out.println("  ||           " + leftArm + torso + rightArm);
        System.out.println(" _||_          " + leftLeg + rightLeg);
        System.out.println("|____|");
    }
    
    
    // Print the high scores
    public static void printHighScores(String gameMode, HashMap<String, Integer> scores) {
        // Create a list of Map.Entry objects and sort it based on values
        List<Map.Entry<String, Integer>> sortedScores = new ArrayList<>(scores.entrySet());
        sortedScores.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        
        // Print the sorted key-value pairs
        System.out.println("----------------- " + gameMode + " scores -----------------");
        int loopCounter = 1;
        for (Map.Entry<String, Integer> entry : sortedScores) {
            System.out.println(loopCounter + ". " + entry.getKey() + ": \t" + entry.getValue() + "\n");
            loopCounter++;
        }
    }
    
    
    //print word/phrasee/name when player loses
    public static void printItem(String category){
        
        if ( category.equals("Random") )
            category = randomCategoryChosen;
        
        switch (category) {
            case "Animals": category = "Animal"; break;
            case "Countries": category = "Country"; break;
            case "Movies": category = "Movie"; break;
            case "Idioms": category = "Saying"; break;
        }
        
        System.out.print(category + ": ");
        for (int i = 0; i < hiddenItem.length(); i++) {
            System.out.print(" " + hiddenItem.charAt(i));
        }
        System.out.println();
    }
    
    
    //Removes person with lowest score from leader board
    public static void removeLowestScore(String gameMode, HashMap<String, Integer> scores) {
        
        
        // Find the player with the lowest score
        String lowestScoringPlayer = null;
        int lowestScore = Integer.MAX_VALUE;

        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            if (entry.getValue() < lowestScore) {
                lowestScore = entry.getValue();
                lowestScoringPlayer = entry.getKey();
            }
        }

        if (lowestScoringPlayer != null) {
            scores.remove(lowestScoringPlayer);
        }
        
    }
    
    
    //selecting a category between animals, countries, movies
    public static String selectCategory() {
        
        //creating scanner object
        Scanner input = new Scanner(System.in);
        
        //instantiating has chosen category variable
        boolean validCategoryChoice = false;
        String category = "";
        
        do {
            //selecting category
            System.out.println("--------------- Selecting Categories ---------------");
            System.out.println("Category Options: 1 = Animals, 2 = Countries, 3 = Movies");
            System.out.print("Enter category: ");
            category = input.next().trim();
            
            if (category.equalsIgnoreCase("stop program"))
                terminateProgram();
            
            //saving and returning category
            if (category.equals("1") || category.equalsIgnoreCase("Animals"))
                category = "Animals";
            else if (category.equals("2") || category.equalsIgnoreCase("Countries"))
                category = "Countries";
            else if (category.equals("3") || category.equalsIgnoreCase("Movies"))
                category = "Movies";
            else {
                System.out.println("Please enter an appropriate input.");
                continue;
            }
            
            validCategoryChoice = true;
        } while (!validCategoryChoice);
            
        return category;
    }
    
    
    //Allows player to select difficulty
    public static int selectDifficulty(String category) {
        
        //creating scanner object
        Scanner input = new Scanner(System.in);
        
        //instantiating has chosen playerChoice variable
        boolean validDifficultyChoice = false;
        int difficulty = 0;
        
        if (category.equals("Random"))
            difficulty = RANDOM;
        else {
            
            do {
                //selecting playerChoice
                System.out.println("--------------- Selecting Difficulty ---------------");
                System.out.println("Category Options: 1 = Easy, 2 = Medium, 3 = Hard");
                System.out.print("Enter difficulty: ");
                String playerChoice = input.next().trim();

                if (playerChoice.equalsIgnoreCase("stop program"))
                    terminateProgram();

                //saving appropriate difficulty depending on playerChoice
                if (playerChoice.equals("1") || playerChoice.equalsIgnoreCase("Easy"))
                    difficulty = EASY;
                else if (playerChoice.equals("2") || playerChoice.equalsIgnoreCase("Medium"))
                    difficulty = MEDIUM;
                else if (playerChoice.equals("3") || playerChoice.equalsIgnoreCase("Hard"))
                    difficulty = HARD;
                else {
                    System.out.println("Please enter an appropriate input.");
                    continue;
                }

                validDifficultyChoice = true;

            } while (!validDifficultyChoice);
            
        }
        //returning difficulty
        return difficulty;
    }
    
    
    public static void startClassicGameMode() {
        
        Scanner input = new Scanner(System.in);
        
        //declaring of variables
        String category;
        String gameMode;
        int difficulty;

        //setting the gameMode variable to classic
        gameMode = "Classic";
        
        //selecting and saving category and difficulty
        category = selectCategory();
        difficulty = selectDifficulty(category);
        
        //setting attemptCount to 0 and selecting word based on category and difficulty
        attemptCount = 0;
        itemsGuessed = 0;
        score = 0;
        switch (category) {
            case "Animals":
                hiddenItem = getItemToBeGuessed(category, difficulty);
                break;
            case "Countries":
                hiddenItem = getItemToBeGuessed(category, difficulty);
                break;
            case "Movies":
                hiddenItem = getItemToBeGuessed(category, difficulty);
                break;
        }
        
        
        //call on startGamePlayLoop method and providing it relevant arguments
        startGamePlayLoop(gameMode, category, difficulty);
        
        boolean isEligible = isScoreEligibleForHighScores(score);
        
        //adding current score to list if it is higher
        if (isEligible) {
            
            addScore(gameMode, score);
            
            removeLowestScore(gameMode, classicScores);
            
            printHighScores(gameMode, classicScores);
        }
    }
    
    
    /*
    starting the main game play loop for all modes
    returns "Win" if player guesses hiddenItem
    returns "Lose" if player fails to guess hiddenItem
    */ 
    public static String startGamePlayLoop(String gameMode, String category, int difficultyIndex) {
        
        List<Character> playerGuesses = new ArrayList<>();
        
        //saving either "Easy", "Medium", or "Hard" to difficulty
        String difficulty = getDifficultyString(difficultyIndex);
        String winOrLose = "";
        int playerScore = 0;
        
        //adding special characters to the playerGuesses list
        if (hiddenItem.contains("-"))
            playerGuesses.add('-');
        if (hiddenItem.contains("'"))
            playerGuesses.add('\'');
        if (hiddenItem.contains(":"))
            playerGuesses.add(':');
        if (hiddenItem.contains(" "))
            playerGuesses.add(' ');
        
        //loop for player to guess words until player wins
        System.out.println("------------ Game Mode --- " + gameMode + " ------------" );
        System.out.println("------------ Category --- " + category + " ------------" );
        System.out.println("------------ Difficulty --- " + difficulty + " ------------" );
        
         
        while (true) {
            
            //print the hanged man
            printHangedMan();
            
            if (attemptCount >= 6) {
                winOrLose = "Lose";
                if (category.equals("Random")) {
                    printItem(category);
                    System.out.println("------------------- Result! -------------------");
                    System.out.println("You Lose.");
                    System.out.println("Items Guessed: " + itemsGuessed);
                    break;
                } else {
                    printItem(category);
                    System.out.println("------------------- Result! -------------------");
                    System.out.println("You Lose.");
                    break;
                }
            }
            
            //ends loop or gives next word if word matches
            if (printCurrentWordState(playerGuesses)) {
                itemsGuessed++;
                winOrLose = "Win";
                playerScore += calculateScore(itemsGuessed, attemptCount);
                
                if (category.equals("Random")) {
                    
                    System.out.println("----------- Item Guessed Correctly ------------");
                    System.out.println("Items Guessed: " + itemsGuessed);
                    System.out.println("Score: " + score + " +" + playerScore);
                    score += playerScore;
                    break;
                    
                } else {
                    System.out.println("------------------- Result! -------------------");
                    System.out.println("You Win!");
                    score += playerScore;
                    break;
                }
            }
            
            getPlayerGuesses(playerGuesses);
            
            System.out.println("-----------------------------------------------");
            System.out.println("------- " + gameMode + " --- " + category + " --- " + difficulty + " -------");
            System.out.println("-----------------------------------------------");
            
        }
        
        return winOrLose;
    }
    
    
    public static void startPhraseGameMode() {
        //declaring objects
        Scanner input = new Scanner(System.in);
        
        //declaring of variables
        String category;
        String gameMode;
        int difficulty;

        //setting the gameMode variable to classic
        gameMode = "Phrase";
        
        //selecting and saving category and difficulty
        category = "Idioms";
        difficulty = selectDifficulty(category);
        
        //saving item into variable and setting attemptCount to 0
        attemptCount = 0;
        itemsGuessed = 0;
        score = 0;
        hiddenItem = getItemToBeGuessed(category, difficulty);
        
        
        
        //call on startGamePlayLoop method and providing it relevant arguments
        startGamePlayLoop(gameMode, category, difficulty);
        
        boolean isEligible = isScoreEligibleForHighScores(score);
        
        //adding current score to list if it is higher
        if (isEligible) {
            
            addScore(gameMode, score);
            
            removeLowestScore(gameMode, phraseScores);
            
            printHighScores(gameMode, phraseScores);
        }
    }
    
    
    public static void startRandoManiaMode() {
        
        //declaring of variables
        String category;
        String roundState;
        String gameMode;
        int difficulty;
        boolean stillHasAttempts = true;

        //setting the gameMode variable to classic
        gameMode = "RandoMania";
        
        //selecting and saving category and difficulty
        category = "Random";
        difficulty = selectDifficulty(category);
        
        //saving item into variable and setting attemptCount to 0
        attemptCount = 0;
        itemsGuessed = 0;
        score = 0;
        
        while (stillHasAttempts) {
            hiddenItem = getItemToBeGuessed(category, difficulty);
            
            //call on startGamePlayLoop method and providing it relevant arguments
            roundState = startGamePlayLoop(gameMode, category, difficulty);
            
            //ending game if player failed to guess word
            stillHasAttempts = roundState.equals("Win");
        }
        
        boolean isEligible = isScoreEligibleForHighScores(score);
        
        //adding current score to list if it is higher
        if (isEligible) {
            
            addScore(gameMode, score);
            
            removeLowestScore(gameMode, randoManiaScores);
            
            printHighScores(gameMode, randoManiaScores);
        }
        
    }
    
    
    // displays scores according to mode selected
    public static void startScoreMode() {
        
        Scanner input = new Scanner(System.in);
        String gameMode;
        
        System.out.println("----------------- Display Scores -----------------");
        System.out.println("Select Game Mode:");
        System.out.println("1. Classic");
        System.out.println("2. Phrase");
        System.out.println("3. RandoMania");
        
        //player input
        System.out.print("Input: ");
        gameMode = input.nextLine();
            
        if (gameMode.equals("1") || gameMode.equalsIgnoreCase("Classic")) {
            gameMode = "Classic";
            printHighScores(gameMode, classicScores);
        }
        else if (gameMode.equals("2") || gameMode.equalsIgnoreCase("Phrase")) {
            gameMode = "Phrase";
            printHighScores(gameMode, phraseScores);
        }
        else if (gameMode.equals("3") || gameMode.equalsIgnoreCase("RandoMania")) {
            gameMode = "RandoMania";
            printHighScores(gameMode, randoManiaScores);
        }
        
        
        //printHighScoreList(gameMode);
    }
    
    
    public static void terminateProgram() {
        System.exit(0);
    }
    
}

