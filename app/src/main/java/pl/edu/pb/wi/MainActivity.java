package pl.edu.pb.wi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button promptButton;
    private TextView questionTextView;
    private int currentIndex = 0;
    private static final String TAG = "Quiz";
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    public static final String KEY_EXTRA_ANSWER = "pl.edu.pb.wi.quiz.correctAnswer";
    private static final int REQUEST_CODE_PROMPT = 0;
    boolean answerWasShown=false;

    private Question[] questions = new Question[]{
            new Question(R.string.q_1,false),
            new Question(R.string.q_2,true),
            new Question(R.string.q_3,false),
            new Question(R.string.q_4,true),
            new Question(R.string.q_5,false)
    };

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "Wywołano metodę onStart");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "Wywołano metodę onResume");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "Wywołano metodę onPause");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "Wywołano metodę onStop");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "Wywołano metodę onDestroy");
    }


    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Log.d(TAG, "Wywołano metodę onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("MainActivity","Wywołano metodę: onActivityResult");
        if(resultCode != RESULT_OK) { return; }
        if (requestCode == REQUEST_CODE_PROMPT){
            if (data == null) { return; }
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Wywołano metodę onCreate");
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null){
           currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        promptButton = findViewById(R.id.tip_button);
        questionTextView = findViewById(R.id.question_text_view);

        setNextQuestion();

        trueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswerCorrectness(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswerCorrectness(false);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener(){

            private void setNextQuestion(){
                questionTextView.setText(questions[currentIndex].getQuestionId());
            }

            @Override
            public void onClick(View v){
                currentIndex = (currentIndex + 1)%questions.length;
                setNextQuestion();
            }

        });

        promptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PromptActivity.class);
                boolean correctAnswer = questions[currentIndex].isTrueAnswer();
                intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
                startActivityForResult(intent, REQUEST_CODE_PROMPT);
            }
        });

    }




    private void checkAnswerCorrectness(boolean userAnswer){
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;
        if(answerWasShown){
            resultMessageId = R.string.answer_was_shown;
        }else{
            if (userAnswer == correctAnswer){
                resultMessageId = R.string.correct_answer;
            } else {
                resultMessageId = R.string.incorrect_answer;
            }
        }

        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }

    private void setNextQuestion(){
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }




}

