package com.kvizznanja;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.kvizznanja.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME ="quiz.db";
    private static final int DATABASE_VERSION =1;

    private static QuizDbHelper instance;

    private SQLiteDatabase db;

    private QuizDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized QuizDbHelper getInstance(Context contex) {
        if(instance == null) {
            instance = new QuizDbHelper(contex.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
            CategoriesTable.TABLE_NAME + "( " +
            CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CategoriesTable.COLUMN_NAME + " TEXT " +
            ")";

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT, " +
                QuestionsTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + QuestionsTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                CategoriesTable.TABLE_NAME + "(" + CategoriesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";

        db.execSQL(SQL_CREATE_CATEGORIES_TABLE);

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillCategoriesTable();
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoriesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);

        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillCategoriesTable() {
        Category c1 = new Category("Sport");
        insertCategory(c1);
        Category c2 = new Category("Zabava");
        insertCategory(c2);
        Category c3 = new Category("Tehnologija");
        insertCategory(c3);
    }

    public void addCategory(Category category) {
        db = getWritableDatabase();
        insertCategory(category);
    }

    public void addCategories(List<Category> categories) {
        db = getWritableDatabase();

        for(Category category : categories) {
            insertCategory(category);
        }
    }

    private void insertCategory(Category category) {
        ContentValues cv = new ContentValues();
        cv.put(CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(CategoriesTable.TABLE_NAME, null, cv);
    }

    private  void fillQuestionsTable() {

        // PITANJA ZA SPORT (Laka) 1-5
        Question q1 = new Question("Sport: Koja drzava je igrala cak tri puta finale svetskog prvenstva u fudbalu," +
                "ali ga nikad nije osvojila o kojoj se drzavi radi? ",
                "Engleska", "Holandija", "Urugvaj", 2,
                Question.DIFFICULTY_EASY, Category.Sport);
        insertQuestion(q1);
        Question q2 = new Question("Sport: On je jedini igrac koje je osvojio Ligu Sampiona sa tri razlicita kluba.Ko je on?",
                "Clarence Seedorf", "Thierry Henry", "Cristiano Ronaldo", 1,
                Question.DIFFICULTY_EASY, Category.Sport);
        insertQuestion(q2);
        Question q3 = new Question("Sport: Barcelona ima najvise osvojenih kupova, odnosno Copa del Reya," +
                "i to 30.Koji je klub na drugom mesto sa osvojenih 23.",
                "Real Madrid", "Atlético Madrid", "Athletic Bilbao", 3,
                Question.DIFFICULTY_EASY, Category.Sport);
        insertQuestion(q3);
        Question q4 = new Question("Sport: Ko je osvojio Zlatnu loptu 2004 godine",
                "Andriy Shevchenko", "Deco", "Ronaldinho", 1,
                Question.DIFFICULTY_EASY, Category.Sport);
        insertQuestion(q4);
        Question q5 = new Question("Sport: Ko ima najvise osvojenih NBA prstenova?",
                "Michael Jordan", "Shaquille O'Neal", "Billi Russell", 3,
                Question.DIFFICULTY_EASY, Category.Sport);
        insertQuestion(q5);

        // ****KRAJ PITANJA ZA SPORT (LAKA)****


        // PITANJA ZA SPORT (SREDNJA) 5-10
        Question q6 = new Question("Sport: U kom sportu se takmice samo zene?",
                "U veslanju", "U streljastvu", "U ritmickoj gimnastici", 3,
                Question.DIFFICULTY_MEDIUM, Category.Sport);
        insertQuestion(q6);
        Question q7 = new Question("Sport: Diro di Italia je?",
                "Auto trka", "Biciklisticka trka", "Moto trka", 2,
                Question.DIFFICULTY_MEDIUM, Category.Sport);
        insertQuestion(q7);
        Question q8 = new Question("Sport: U kojoj zemlji je nastao tenis?",
                "Italija", "Amerika", "Engleska", 3,
                Question.DIFFICULTY_MEDIUM, Category.Sport);
        insertQuestion(q8);
        Question q9 = new Question("Sport: Koliko titula svetskog prvaka ima fudbalska reprezentacija Nemacke?",
                "Cetiri", "Dve", "Tri", 1,
                Question.DIFFICULTY_MEDIUM, Category.Sport);
        insertQuestion(q9);
        Question q10 = new Question("Sport: U koju boksersku kategoriju spadaju takmicari izmedju 81 do 90 kilograma?",
                "Srednja", "Poluteska", "Teska", 3,
                Question.DIFFICULTY_MEDIUM, Category.Sport);
        insertQuestion(q10);

        // ****KRAJ PITANJA ZA SPORT (SREDNJA)****


        //PITANJA ZA SPORT (TESKA) 10-15
        Question q11 = new Question("Sport: Ironman je takmicenje u?",
                "Dizanju tegova", "Kik boksu", "Triatlonu", 3,
                Question.DIFFICULTY_HARD, Category.Sport);
        insertQuestion(q11);
        Question q12 = new Question("Sport: Moderni ragbi nastao je u?",
                "Engleskoj", "Nemackoj", "Kanada", 1,
                Question.DIFFICULTY_HARD, Category.Sport);
        insertQuestion(q12);
        Question q13 = new Question("Sport: Ko je igrao zaa LA Lejkerse?",
                "Derik Rouz", "Karl Meloun", "Derik Fiser", 3,
                Question.DIFFICULTY_HARD, Category.Sport);
        insertQuestion(q13);
        Question q14 = new Question("Sport: Koliko igraca ima na terenu jedna ekipa u hokeju na travi?",
                "Jedanaest", "Petnaest", "Sest", 1,
                Question.DIFFICULTY_HARD, Category.Sport);
        insertQuestion(q14);
        Question q15 = new Question("Sport: Kojim rezultatom je Francuska 1998. godine pobedila Brazil na svetskom prvenstvu u fudbalu?",
                "1:0", "3:0", "2:1", 2,
                Question.DIFFICULTY_HARD, Category.Sport);
        insertQuestion(q15);

        //****KRAJ PITANJA ZA SPORT (TESKA)****


        //PITANJA ZA ZABAVU (LAKA) 15-20
        Question q16 = new Question("Zabava: Koliko postoji kontinenta na zemlji?",
                "6", "8", "7", 3,
                Question.DIFFICULTY_EASY, Category.Zabava);
        insertQuestion(q16);
        Question q17 = new Question("Zabava: Koja zemlja ima najduzu obalu na svetu?",
                "Kanada", "SAD", "Australija", 1,
                Question.DIFFICULTY_EASY, Category.Zabava);
        insertQuestion(q17);
        Question q18 = new Question("Zabava: Koji glumac/glumica ima najvise osvojenih oskara?",
                "Jack Nicholson", "Katharine Hepburn", "Merly Streep", 2,
                Question.DIFFICULTY_EASY, Category.Zabava);
        insertQuestion(q18);
        Question q19 = new Question("Zabava: Koja pesma ima najvise pregleda na YouTube-u?",
                "Despacito", "Shape of You", "Gangam Style", 1,
                Question.DIFFICULTY_EASY, Category.Zabava);
        insertQuestion(q19);
        Question q20 = new Question("Zabava: U kojoj drzavi se nalazi najvise vulkana?",
                "Filipini", "Indonezija", "Japan", 2,
                Question.DIFFICULTY_EASY, Category.Zabava);
        insertQuestion(q20);

        // ****KRAJ PITANJA ZA ZABAVU (LAKA) ****


        // PITANJA ZA ZABAVU (SREDNJA) 20-25
        Question q21 = new Question("Zabava: Najveca piramida u Egiptu je?",
                "Keopsova piramida", "Kefrenova piramida", "Sneferuova piramida", 1,
                Question.DIFFICULTY_MEDIUM, Category.Zabava);
        insertQuestion(q21);
        Question q22 = new Question("Zabava: Epski roman 'Rat i mir' napisao je?",
                "Sergej Aleksandrovic Jesenjin", "Fjodor Mihajlovic Dostojevski", "Lav Nikolajevic Tolstoj", 3,
                Question.DIFFICULTY_MEDIUM, Category.Zabava);
        insertQuestion(q22);
        Question q23 = new Question("Zabava: Machu Picchu je sveti grad?",
                "Inka", "Maya", "Azteca", 1,
                Question.DIFFICULTY_MEDIUM, Category.Zabava);
        insertQuestion(q23);
        Question q24 = new Question("Zabava: Pored macaka koje jos zivotinje predu?",
                "Nilski konji", "Veverice", "Zirafe", 2,
                Question.DIFFICULTY_MEDIUM, Category.Zabava);
        insertQuestion(q24);
        Question q25 = new Question("Zabava: Ko je bio prvi covek u Svemiru?",
                "Sergei Krikalev", "Yuri Gagarin", "Neil Armstrong", 2,
                Question.DIFFICULTY_MEDIUM, Category.Zabava);
        insertQuestion(q25);

        // ****KRAJ PITANJA ZA ZABAVU (SREDNJA)****


        // PITANJA ZA ZABAVU (TESKA) 25-30
        Question q26 = new Question("Zabava: Od cega je SMS skracenica?",
                "Short Message Service", "Safe Message Sending", "Secret Message Service", 1,
                Question.DIFFICULTY_HARD, Category.Zabava);
        insertQuestion(q26);
        Question q27 = new Question("Zabava: Koji od kraljeva u standardizovanim kartama za igru, najcesce nema brkove?",
                "Karo", "Pik", "Herc", 3,
                Question.DIFFICULTY_HARD, Category.Zabava);
        insertQuestion(q27);
        Question q28 = new Question("Zabava: Koje godine je preminuo Fidel Kastro?",
                "2016", "2013", "2010", 1,
                Question.DIFFICULTY_HARD, Category.Zabava);
        insertQuestion(q28);
        Question q29 = new Question("Zabava: Sredstva za odbijanje insekata zovu se?",
                "Insekticidi", "Repelanti", "Iritanti", 2,
                Question.DIFFICULTY_HARD, Category.Zabava);
        insertQuestion(q29);
        Question q30 = new Question("Zabava: Mitolosko bice koje je imalo kozje telo, zmijski ili zmajski rep " +
                "te lavlju glavu naziva se?",
                "Himera", "Kerber", "Satir", 1,
                Question.DIFFICULTY_HARD, Category.Zabava);
        insertQuestion(q30);

        // ****KRAJ PITANJA ZA ZABAVU (TESKA)


        // PITANAJ ZA Tehniku (LAKA) 30-35
        Question q31 = new Question("Tehnika: Na koliko je Twitter ogranicio broj znakova po tvitu? ",
                "200", "140", "500", 2,
                Question.DIFFICULTY_EASY, Category.Tehnika);
        insertQuestion(q31);
        Question q32 = new Question("Tehnika: Koje godine je Apple predstavio prvi iPhone? ",
                "2005", "2009", "2007", 3,
                Question.DIFFICULTY_EASY, Category.Tehnika);
        insertQuestion(q32);
        Question q33 = new Question("Tehnika: Koja je najveca mera za kolicinu informacija? ",
                "Terabajt", "Gigabajt", "Megabajt", 1,
                Question.DIFFICULTY_EASY, Category.Tehnika);
        insertQuestion(q33);
        Question q34 = new Question("Tehnika: Koje godine je Apple predstavio prvi iPhone? ",
                "2005", "2009", "2007", 3,
                Question.DIFFICULTY_EASY, Category.Tehnika);
        insertQuestion(q34);
        Question q35 = new Question("Tehnika: Koja softverska aplikacija se koristi za pristup informacijama i web stranicama? ",
                "Web Browser", "Operativni Sistem", "Microsoft Word", 1,
                Question.DIFFICULTY_EASY, Category.Tehnika);
        insertQuestion(q35);

        // ****KRAJ PITANJA ZA Tehniku (LAKA)****


        // PITANJA ZA Tehniku (SREDNJA) 35-40
        Question q36 = new Question("Tehnika: Koja softverska aplikacija se koristi za pristup informacijama i web stranicama? ",
                "Web Browser", "Operativni Sistem", "Microsoft Word", 1,
                Question.DIFFICULTY_MEDIUM, Category.Tehnika);
        insertQuestion(q36);
        Question q37 = new Question("Tehnika: Koji od navedenih ne moze da cuva podatke? ",
                "Floppy Disk", "Mis", "Hard Disk", 2,
                Question.DIFFICULTY_MEDIUM, Category.Tehnika);
        insertQuestion(q37);
        Question q38 = new Question("Tehnika: Sa njom se definise vremenski interval u kojem korisnik komunicira sa web aplikacijom. O cemu govorimo? ",
                "O upitu", "O aplikaciji", "o Sesiji", 3,
                Question.DIFFICULTY_MEDIUM, Category.Tehnika);
        insertQuestion(q38);
        Question q39 = new Question("Tehnika: Za sta se vezuje termin Pentium? ",
                "Mikroprocesor", "DVD", "Mis", 1,
                Question.DIFFICULTY_MEDIUM, Category.Tehnika);
        insertQuestion(q39);
        Question q40 = new Question("Tehnika: Od cega je skracenica JVM? ",
                "Java Version Machine", "Java Virtual Machine", "Jumbo Virtual Machine", 2,
                Question.DIFFICULTY_MEDIUM, Category.Tehnika);
        insertQuestion(q40);

        // ****KRAJ PITANJA ZA Tehniku (SREDNJA)****


        // PITANJA ZA Tehniku (TESKA)
        Question q41 = new Question("Tehnika: Sta je skracenica BIOS? ",
                "Better Integerated Operating System", "Backup Input Output System", "Basic Input Output System", 3,
                Question.DIFFICULTY_HARD, Category.Tehnika);
        insertQuestion(q41);
        Question q42 = new Question("Tehnika: To je mali komad teksta, sacuvan od strane web browsera" +
                " na korisnikov racunar. O cemu pricamo? ",
                "Session", "Cookie", "Application", 2,
                Question.DIFFICULTY_HARD, Category.Tehnika);
        insertQuestion(q42);
        Question q43 = new Question("Tehnika: Prvi masovno popularan web pretrazivac objavljen 1993 godine, bio je?",
                "Netscape Navigator", "Mosaic", "Internet Explorer", 2,
                Question.DIFFICULTY_HARD, Category.Tehnika);
        insertQuestion(q43);
        Question q44 = new Question("Tehnika: Koja od navedenih logickih kola se koristi kao memorijski uredjaj u racunarskoj tehnici?",
                "Flip-Flop", "Komparator", "Memorizator", 1,
                Question.DIFFICULTY_HARD, Category.Tehnika);
        insertQuestion(q44);
        Question q45 = new Question("Tehnika: Od cega je skracenica ATM?",
                "Automatic Teller Machine", "Any Time Machine", "Any Time Money", 1,
                Question.DIFFICULTY_HARD, Category.Tehnika);
        insertQuestion(q45);

        // ****KRAJ PITANJA ZA Tehniku (TESKA)****



    }

    public void addQuestion(Question question) {
        db = getWritableDatabase();
        insertQuestion(question);
    }

    public void addQuestions(List<Question> questions) {
        db = getWritableDatabase();

        for (Question question : questions) {
            insertQuestion(question);
        }
    }


    private void insertQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        cv.put(QuestionsTable.COLUMN_CATEGORY_ID, question.getCategoryID());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CategoriesTable.TABLE_NAME, null);

        if(c.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(c.getInt(c.getColumnIndex(CategoriesTable._ID)));
                category.setName(c.getString(c.getColumnIndex(CategoriesTable.COLUMN_NAME)));
                categoryList.add(category);
            } while (c.moveToNext());
        }
        c.close();
        return categoryList;
    }

    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);
        if(c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while(c.moveToNext());
        }

        c.close();
        return questionList;
    }

    // PART FOR DIFFICULTY
    public ArrayList<Question> getQuestions(int categoryID, String difficulty) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String selection = QuestionsTable.COLUMN_CATEGORY_ID + " = ? " +
        " AND " + QuestionsTable.COLUMN_DIFFICULTY + " = ? ";

        String[] selectionArgs = new String[] { String.valueOf(categoryID), difficulty };

        Cursor c = db.query(
                QuestionsTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if(c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while(c.moveToNext());
        }

        c.close();
        return questionList;
    }
}
