package com.jovialsa.myquiz2;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ThirdFragment extends Fragment {

    private TextView textQuestion;
    private TextView textViewAciertos;
    private TextView textViewFallos;
    private Button mButtonSendAnswer;
    private RadioButton answer1;
    private RadioButton answer2;
    private RadioButton answer3;
    private RadioButton answer0;
    private RadioGroup answerGroup;

    private ArrayList<QuizQuestion> questions;
    private QuizQuestion question;


    private static final String ACIERTOS = "ACIERTOS";
    private static final String FALLOS = "FALLOS";
    private static final String TOTAL = "TOTAL";
    private static final String ACTUAL = "ACTUAL";

    private int aciertos;
    private int fallos;

    private View vista;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Bundle bundle = getArguments();

        if (bundle == null) {

            Log.d("ThirdFragment", " savedInstaceState is null");

            aciertos = 0;
            fallos = 0;

            bundle = new Bundle();
            bundle.putInt(ACIERTOS, aciertos);
            bundle.putInt(FALLOS, fallos);
            bundle.putInt(TOTAL,5);
            bundle.putInt(ACTUAL,1);

            setArguments(bundle);

        } else {
            aciertos = bundle.getInt(ACIERTOS);
            fallos = bundle.getInt(FALLOS);
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializar(view);

        Random r = new Random();


        try {
            vista = view;
            questions = readQuestions();
            question = questions.get(r.nextInt(questions.size()));

            // Generamos los indices y barajamos para colocar las respuestas en posiciones aleatorias
            ArrayList<Integer> index = new ArrayList<Integer>();
            for (int i = 0; i <= 3; i++)
                index.add(i);

            Collections.shuffle(index);

            textQuestion.setText(question.getQuestion());
            answer0.setText(question.getAnswer(index.get(0)));
            answer1.setText(question.getAnswer(index.get(1)));
            answer2.setText(question.getAnswer(index.get(2)));
            answer3.setText(question.getAnswer(index.get(3)));


            textViewAciertos.setText(String.valueOf(aciertos));
            textViewFallos.setText(String.valueOf(fallos));


        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        view.findViewById(R.id.button_third).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                int idSelected = answerGroup.getCheckedRadioButtonId();
                if (idSelected != -1) {

                    RadioButton radioButtonSelected = (RadioButton) vista.findViewById(idSelected);

                    String answer = radioButtonSelected.getText().toString();

                    if (question.isCorrect(answer)) {
                        aciertos++;
                        Bundle bundle = getArguments();
                        bundle.putInt(ACIERTOS, aciertos);

                        NavHostFragment.findNavController(ThirdFragment.this)
                                .navigate(R.id.action_ThirdFragment_to_FourthFragment, bundle);
                    } else {
                        fallos++;
                        Bundle bundle = getArguments();
                        bundle.putInt(FALLOS, fallos);
                        NavHostFragment.findNavController(ThirdFragment.this)
                                .navigate(R.id.action_ThirdFragment_to_FifthFragment,getArguments());
                    }

                } else {
                    Toast.makeText(view.getContext(), "Debe seleccionar previamente una respuesta", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public void onStop() {
        super.onStop();

        Bundle bundle = getArguments();
        if (bundle != null) {
            Log.d("Third", "Aciertos: " + bundle.getInt(ACIERTOS) + "   Fallos: " + bundle.getInt(FALLOS));
        } else
            Log.d("Third", "Datos no accesibles");

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putInt(ACIERTOS, aciertos);
        savedInstanceState.putInt(FALLOS, fallos);

        super.onSaveInstanceState(savedInstanceState);

    }

    private void inicializar(View view) {

        textQuestion = (TextView) view.findViewById(R.id.textview_question);
        textViewAciertos = (TextView) view.findViewById(R.id.textViewAciertos);
        textViewFallos = (TextView) view.findViewById(R.id.textViewFallos);
        mButtonSendAnswer = (Button) view.findViewById(R.id.button_third);
        answer1 = (RadioButton) view.findViewById(R.id.answer1);
        answer2 = (RadioButton) view.findViewById(R.id.answer2);
        answer3 = (RadioButton) view.findViewById(R.id.answer3);
        answer0 = (RadioButton) view.findViewById(R.id.answer0);
        answerGroup = (RadioGroup) view.findViewById(R.id.answerGroup);

    }

    private ArrayList<QuizQuestion> readQuestions() throws IOException, XmlPullParserException {

        String logTag = "Lectura de XML";
        String lastErrorMessage = "";
        Resources res = getResources();
        XmlResourceParser quizDataXmlParser = res.getXml(R.xml.quiz_data);

        ArrayList<String> xmlTagStack = new ArrayList<>();
        ArrayList<QuizQuestion> quizQuestions = new ArrayList<>();

        QuizQuestion currentQuestion = null;

        boolean isCurrentAnswerCorrect = false;

        quizDataXmlParser.next();
        int eventType = quizDataXmlParser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            //  begin document
            if (eventType == XmlPullParser.START_DOCUMENT) {
                Log.d(logTag, "Begin Document");
            }
            //  begin tag
            else if (eventType == XmlPullParser.START_TAG) {
                String tagName = quizDataXmlParser.getName();
                xmlTagStack.add(tagName);
                Log.d(logTag, "Begin Tag " + tagName + ", depth: " + xmlTagStack.size());
                Log.d(logTag, "Tag " + tagName + " has " + quizDataXmlParser.getAttributeCount() + " attribute(s)");

                // this is a beginning of a quiz question tag so create a new QuizQuestion object
                if (tagName.equals("quizquestion")) {
                    currentQuestion = new QuizQuestion();
                } else if (tagName.equals("answer")) {
                    isCurrentAnswerCorrect = quizDataXmlParser.getAttributeBooleanValue(null, "correct", false);
                    if (isCurrentAnswerCorrect == true) {
                        Log.d(logTag, "Tag " + tagName + " has attribute correct = true");
                    } else {
                        Log.d(logTag, "Tag " + tagName + " has attribute correct = false");
                    }
                }
            }
            //  end tag
            else if (eventType == XmlPullParser.END_TAG) {
                String tagName = quizDataXmlParser.getName();
                if (xmlTagStack.size() < 1) {
                    lastErrorMessage = "Error 101: encountered END_TAG " + quizDataXmlParser.getName() + " while TagStack is empty";
                    Log.e(logTag, lastErrorMessage);
                    return null;
                }
                xmlTagStack.remove(xmlTagStack.size() - 1);
                Log.d(logTag, "End Tag " + quizDataXmlParser.getName() + ", depth: " + xmlTagStack.size());

                //  reached the end of a quizquestion definition, add it to the array
                if (tagName.equals("quizquestion")) {
                    if (currentQuestion != null)
                        quizQuestions.add(currentQuestion);
                    currentQuestion = null;
                }
            }
            //  text between tag begin and end
            else if (eventType == XmlPullParser.TEXT) {
                String currentTag = xmlTagStack.get(xmlTagStack.size() - 1);
                String text = quizDataXmlParser.getText();
                Log.d(logTag, "Text: " + text + ", current tag: " + currentTag + ", depth: " + xmlTagStack.size());

                if (currentQuestion == null) {
                    Log.e(logTag, "currentQuestion is not initialized! text: " + text + ", current tag: " + currentTag + ", depth: " + xmlTagStack.size());
                    continue;
                }

                if (currentTag.equals("question")) {
                    currentQuestion.setQuestion(text);
                } else if (currentTag.equals("answer")) {
                    currentQuestion.addAnswer(text, isCurrentAnswerCorrect);
                } else {
                    Log.e(logTag, "Unexpected tag " + currentTag + " with text: " + text + ", depth: " + xmlTagStack.size());
                }
            }
            eventType = quizDataXmlParser.next();
        }
        Log.d(logTag, "End Document");
        return quizQuestions;

    }
}
