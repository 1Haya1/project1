import java.util.Random;
import java.util.Scanner;


public class Main {
    // المتغيرات الاساسيه
    static String[][] board;
    static String turn;// الادوار
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();// لارقام عشوائيه

    // الميين عشان تبدا اللعبه
    public static void main(String[] args) {

        System.out.println("Welcome to Tic Tac Toe!");
        System.out.println("Let's have some fun!");

        // لوب لبدا اللعبه
        boolean playAgain = true;
        while (playAgain) {
            // اسامي الاعبين
            System.out.print("Enter Player's name: ");
            String player1Name = scanner.nextLine();

            // اختيار الخصم
            char opponent = chooseOpponent();

            // اختيار عدد الجولات
            int rounds = selectRounds();
            int currentRound = 1;
            int player1Wins = 0;

            // بدا الجولات
            while (currentRound <= rounds) {// يتم الاستمرار مادام عدد الجولات التي تم لعبها اقل او تساوي العدد الاجمالي للجولات المحدده مسبقا
                // اللوحه اعدادها
                initializeBoard();
                //اختيار x,o
                char chosenSymbol = chooseSymbol();
                String player1Symbol, player2Symbol;// رموز الاعبين
                player1Symbol = (chosenSymbol == 'X') ? "X" : "O";// تعني ان الاعب الاول بيكون له الرمز الي يختاره والثاني بياخذ الرمز الثاني الي ماختاره الاول
                player2Symbol = (chosenSymbol == 'X') ? "O" : "X";
            //  يطبع رساله توضح بدايه الجوله الحاليه ويعرض اللوحه
                System.out.println("\nRound " + currentRound + "!");
                displayBoard();

                boolean roundOver = false;// ان الجوله لم تنته بعد
                String currentPlayer = player1Symbol;//رمز الاعب 1 لبدء الجوله

                // بدا الجولات
                while (!roundOver) {// الحلقه تستمر مادامت الجوله لم تنتهي
                    if (currentPlayer.equals(player1Symbol)) {// اذا الاعب الحالي هو لاعب1
                        playerMove(currentPlayer, player1Name);// يطلب منه القيام بحركته
                    } else {
                        if (opponent == 'P') {
                            playerMove(player2Symbol, "Player 2");
                        } else {
                            computerMove(player2Symbol);
                        }
                    }

                    displayBoard();

                    String winner = checkWinner(); //يتحقق اذا فيه فائز بالجوله الحاليه ويحفظ النتيجه
                    if (winner != null) {// يعني فيه فائز
                        if (winner.equals(player1Symbol)) {
                            System.out.println(player1Name + " wins Round " + currentRound + "!");
                            player1Wins++;
                        } else if (winner.equals(player2Symbol)) {
                            if (opponent == 'P') {
                                System.out.println("Player 2 wins Round " + currentRound + "!");
                            } else {
                                System.out.println("Computer wins Round " + currentRound + "!");
                            }
                        } else {// اذا تعادل
                            System.out.println("Round " + currentRound + " is a tie!");
                        }
                        roundOver = true;// يعني ان الجوله انتهت
                    }
                  // اذا كان الاعب الحالي هو الاعب 1 يتم تبديله للاعب 2 للجوله التاليه
                    currentPlayer = (currentPlayer.equals(player1Symbol)) ? player2Symbol : player1Symbol;
                }

                currentRound++;// للاشاره ان الجوله التاليه نزيد يعني 1
            }

            // عرض الفائز
            if (player1Wins > rounds / 2) {
                System.out.println(player1Name + " wins the game!");
            } else {
                if (opponent == 'P') {
                    System.out.println("Player 2 wins the game!");
                } else {
                    System.out.println("Computer wins the game!");
                }
            }

            // السؤال عما إذا كان اللاعب يريد اللعب مرة أخرى وطلب اسم اللاعب إذا اختار اللعب مرة أخرى
            System.out.println("Do you want to play again? (yes/no)");
            String playChoice = scanner.next();
            scanner.nextLine();
            playAgain = playChoice.equalsIgnoreCase("yes");
        }


        System.out.println("Thanks for playing Tic Tac Toe!");
    }

    // قيم افتراضيه في اللوحه
    static void initializeBoard() {// انشاء اللوحه كمصفوفه ثنائيه
        board = new String[][]{{"1", "2", "3"}, {"4", "5", "6"}, {"7", "8", "9"}};
        turn = "X";// يعني ان اللعبه ستبدا مع الاعب اي لاعب ياتي ع الدور
    }

    // عرض لوحه
    static void displayBoard() {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {// للتكرار عبر صفوف اللوحه
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {// لتكرار عبر اعمده اللوحه
                System.out.print(board[i][j] + " | ");// يطبع قيمه الخانه المحدده في الصف والعامود مع الفاصل
            }
            System.out.println("\n-------------");// تطبع خط افقي بعد انتهاء طباعه كل صف
        }
    }

    // داله عشان يختار الاعب x,o
    static char chooseSymbol() {
        char chosenSymbol;

        do {
            System.out.println("Choose X or O:");
            chosenSymbol = scanner.next().toUpperCase().charAt(0);
        } while (chosenSymbol != 'X' && chosenSymbol != 'O');

        return chosenSymbol;
    }

    // Function عشان يختار عدد الجولات
    static int selectRounds() {
        int rounds;

        do {// يضمن ينفذ الكود ع الاقل مرا وحده في حال عدم تحقق الشرط
            System.out.println("How many rounds do you want to play? 1 or 3");
            rounds = scanner.nextInt();
        } while (rounds != 1 && rounds != 3);// اذا كان مو 1ولا3 ريتيرن الراوند يطلب يدخل مرا ثانيه

        return rounds;
    }

    // Function عشان تنفذ حركه الاعب
    static void playerMove(String player, String playerName) {
        System.out.println(playerName + ", enter your move (1-9): ");// يطبع اسم الاعب
        int move = scanner.nextInt();//يقرا الحركه الي ادخلها الاعب

        if (isValidMove(move)) {// يتحقق من صحه حركه الاعب
            // Convert the move to 2D array indices
            int row = (move - 1) / 3;// لتحويل الحركه من ترقيم الخانات 1-9 الى مؤشر صفي
            int col = (move - 1) % 3;// وهذا عامودي
            board[row][col] = player;//اذا كانت حركه الاعب صحيحه يحدث المصفوفه ويحط رمز الاعب
        } else {
            System.out.println("Invalid move. Try again.");
            playerMove(player, playerName);//يطلب من الاعب الاعاده اذا  خطا
        }
    }

    // Function for computer's move
    static void computerMove(String player) {
        int move; // حركه الكمبيوتر رقم الاندكس
        do {// يتم تكرار حتى يتم العثور ع حركه صالحه
            move = random.nextInt(9) + 1; // توليد عدد عشوائي بين 1و9 الي يمثل الموقع باللوحه
        } while (!isValidMove(move));// يتحقق اذا الحركه صالحه او لا

        // تحويل الحركه الى مؤشرات للوحه2D array ,,, تحويل رقم الخانه الي اختارها الاعب الى احداثيات صف وعمود في اللوحه
        int row = (move - 1) / 3;//رقم الخانه الي اختارها الاعب قسمه 3 والتي توزع الخلايا الي صفوف بحيث كل صف يحتوي على 3 خلايا
        int col = (move - 1) % 3;//الباقي عند القسمه ع 3 والتي تعطينا العمود المتبقي بعد توزيع الخلايا في صفوف

        System.out.println("Computer placed " + player + " at position " + move);
        board[row][col] = player;
    }

    // لتحقق من صحه الحركه
    static boolean isValidMove(int move) {
        // اذا ضمن نطاق 1-9 و اذا الموقع لايحتوي على x,o متاح او لا
        return move >= 1 && move <= 9 && !board[(move - 1) / 3][(move - 1) % 3].equals("X") && !board[(move - 1) / 3][(move - 1) % 3].equals("O");
    }

    // Function to check the winner
    static String checkWinner() {
        // Check rows, columns, والقطرين
        for (int i = 0; i < 3; i++) {// لتمرير عبر كل صف وعمود اذا القيم متساويه في الخانات المحدده و 0-2
            if (board[i][0].equals(board[i][1]) && board[i][0].equals(board[i][2])) {
                return board[i][0];// هنا يتم فحص الصفوف اذا كانت القيم متساويه يعني فيه فائز بهذا الصف يرجع قيمه الاعب الفائز
            }
            if (board[0][i].equals(board[1][i]) && board[0][i].equals(board[2][i])) {
                return board[0][i];// يفحص الاعمده
            }
        }
        if (board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2])) {
            return board[0][0];// يفحص القطر من الزاويه العلويه اليسرى الى الزاويه السفليه اليمنى
        }
        if (board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0])) {
            return board[0][2];// هذا الزاويه العلويه اليمنى الى الزاويه السفليه اليسرى
        }

        // Check for اذا انتهت بتعادل انه يتحقق من كل خليه عن طريق عدم احتلال اي لاعب لاي خليه
        boolean isDraw = true; //متغير للتحقق مما اذا انتهت اللعبه بتعادل او لا فرضنا انها انتهت بتعادل ترو
        for (int i = 0; i < 3; i++) {// استخدمت حلقتين متداخله للمرور عبر كل خليه في اللوحهi,j صفوف واعمده
            for (int j = 0; j < 3; j++) {
                if (!board[i][j].equals("X") && !board[i][j].equals("O")) {// يتحقق اذا كانت الخليه في اللوحه فارغه ولاتحتوي على x,o مما يعني انها متاحه للعب
                    isDraw = false;
                    break;
                }
            }
        }
        if (isDraw) {//بعد الانتهاء من الفحص ولا يزال ترو تعادل فيرجع دراو
            return "draw";
        }

        return null; // واذا لم يتم العثور على تعادل فولس فيرجع قيمه فارغه يعني ان اللعبه مستمره
    }

    // Function عشان يختار خصمه
    static char chooseOpponent() {
        char opponent; // Char C or P لتخزين اختيار المستخدم
        do {// في حال عدم تحقق الشرط الموجود بعد كل تنفيذ يضمن ينفذ بين الاقواس مرا واحده ع الاقل
            System.out.println("Do you want to play against a player (P) or the computer (C)?");
            opponent = scanner.next().toUpperCase().charAt(0);// يقرا اختيار المستخدم ويحوله لحرف كبير ويستخرج الحرف الاول 0
        } while (opponent != 'P' && opponent != 'C'); // اذا اختار غلط ريتيرن السؤال مرا ثانيه
        return opponent;
    }
}

