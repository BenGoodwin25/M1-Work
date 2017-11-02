package com.example.user.tp2quizz;

import android.provider.BaseColumns;

public class DatabaseContract {
    private DatabaseContract(){}

    public static class TableQuizz implements BaseColumns{
        public static final String TABLE_NAME = "Quizz";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TYPE = "type";
    }

    public static class TableQuestion implements BaseColumns{
        public static final String TABLE_NAME = "Question";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TEXT = "texte";
        public static final String COLUMN_NAME_REPONSE = "reponse";
        public static final String COLUMN_NAME_QUIZZ = "quizz";
    }

    public static class TableProposition implements BaseColumns{
        public static final String TABLE_NAME = "Proposition";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TEXT = "texte";
        public static final String COLUMN_NAME_QUESTION = "question";
    }
}
