package com.example.miniquiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OkienkoPytania extends AppCompatActivity {
    int index = 0;
    int points = 0;
    boolean ignoreEvent = false;
    RadioGroup group;
    TextView q1;
    RadioButton a1, b1, c1;
    Button reset;
    TextView wynik;
    List<Question> allQuestions = new ArrayList<>();
    List<Question> testQuestions = new ArrayList<>();
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_okienko_pytania);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        q1 = findViewById(R.id.txtPytanie);
        a1 = findViewById(R.id.odpA);
        b1 = findViewById(R.id.odpB);
        c1 = findViewById(R.id.odpC);
        reset = findViewById(R.id.Rbtn);
        wynik = findViewById(R.id.txtWynik2);
        group = findViewById(R.id.group);

        group.setOnCheckedChangeListener((g, checkedId) -> {
            if (ignoreEvent) return;
            if (checkedId == -1) return;

            RadioButton selected = findViewById(checkedId);
            String answer = selected.getText().toString().trim();

            Question current = testQuestions.get(index);

            if (answer.equals(current.correct)) {
                points++;
            }

            wynik.setText("Wynik: " + points);
            group.setEnabled(false);

            group.postDelayed(() -> {
                index++;
                if (index < testQuestions.size()) {
                    ignoreEvent = true;
                    group.clearCheck();
                    ignoreEvent = false;

                    loadQuestion();
                    group.setEnabled(true);
                }
                else {
                    Toast.makeText(this, "Koniec quizu! " + points + "/5", Toast.LENGTH_LONG).show();
                    goToMain();
                }
            }, 300);
        });

        reset.setOnClickListener(view -> {
            index = 0;
            points = 0;
            group.clearCheck();

            generateTest();
            goToMain();
        });

        initQuestions();
        generateTest();
        loadQuestion();
    }
    private void generateTest() {
        testQuestions.clear();
        ArrayList<Question> copy = new ArrayList<>(allQuestions);

        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(copy.size());
            testQuestions.add(copy.get(index));
            copy.remove(index);
        }
    }
    private void loadQuestion() {
        if (index >= testQuestions.size()) return;
        Question q = testQuestions.get(index);

        q1.setText(q.question);
        a1.setText(q.a);
        b1.setText(q.b);
        c1.setText(q.c);
    }
    private void goToMain() {
        Intent intent = new Intent(OkienkoPytania.this, MainActivity.class);
        intent.putExtra("points", points);
        startActivity(intent);
        finish();
    }
    private void initQuestions() {
        allQuestions.add(new Question("Stolica Polski?", "Warszawa", "Kraków", "Gdańsk", "Warszawa"));
        allQuestions.add(new Question("2 + 2 = ?", "3", "4", "5", "4"));
        allQuestions.add(new Question("Największa planeta?", "Mars", "Jowisz", "Ziemia", "Jowisz"));
        allQuestions.add(new Question("Ile dni ma tydzień?", "5", "7", "10", "7"));
        allQuestions.add(new Question("Java to?", "język", "auto", "gra", "język"));
        allQuestions.add(new Question("Najmniejsza planeta Układu Słonecznego?", "Merkury", "Mars", "Wenus", "Merkury"));
        allQuestions.add(new Question("Ile kontynentów jest na Ziemi?", "5", "6", "7", "7"));
        allQuestions.add(new Question("Który pierwiastek ma symbol O?", "Złoto", "Tlen", "Wodór", "Tlen"));
        allQuestions.add(new Question("Który język jest używany do tworzenia aplikacji Android?", "Java", "Python", "C++", "Java"));
        allQuestions.add(new Question("Ile godzin ma doba?", "12", "24", "48", "24"));
        allQuestions.add(new Question("Które zwierzę jest ssakiem?", "Rekin", "Delfin", "Krokodyl", "Delfin"));
        allQuestions.add(new Question("Który kraj ma stolicę Berlin?", "Francja", "Niemcy", "Włochy", "Niemcy"));
        allQuestions.add(new Question("Ile miesięcy ma rok?", "10", "12", "14", "12"));
    }
}
