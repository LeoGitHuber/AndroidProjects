package com.example.digitalsequence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);
        Button button10 = (Button) findViewById(R.id.button10);
        Button button11 = (Button) findViewById(R.id.button11);
        Button button12 = (Button) findViewById(R.id.button12);
        Button button13 = (Button) findViewById(R.id.button13);
        Button button14 = (Button) findViewById(R.id.button14);
        Button button15 = (Button) findViewById(R.id.button15);
        Button button16 = (Button) findViewById(R.id.button16);
        Button button17 = (Button) findViewById(R.id.button17);
        Button button18 = (Button) findViewById(R.id.button18);
        Button button19 = (Button) findViewById(R.id.button19);
        Button[][] buttons = {{button1, button2, button3, button10}, {button4, button5, button6, button11}, {button7, button8, button9, button12}, {button13, button14, button15, button16}};

        DataStore dataStore = new DataStore();
        initialize(buttons, button17, dataStore);

        Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                int[] temp = bundle.getIntArray("switch");
                int[][] tempRecords = dataStore.getRecords();
                button18.setText("自动完成(" + (temp[0] - temp[1]) + ")");
                if (temp[0] == temp[1]) {
                    button18.setText("自动完成");
                    int[] dot = dataStore.getDot();
                    dot[2] = temp[8];
                    dot[0] = tempRecords[temp[8]][0];
                    dot[1] = tempRecords[temp[8]][1];
                    Toast.makeText(MainActivity.this,"Finish!", Toast.LENGTH_SHORT).show();
                    for (Button[] buttons1 : buttons) {
                        for (Button button : buttons1) button.setClickable(true);
                    }
                    button17.setClickable(true);
                    button19.setClickable(true);
                    button18.setClickable(true);
                }
                tempRecords[temp[2]][2] = temp[3];
                tempRecords[temp[2]][3] = temp[4];
                buttons[msg.arg1][msg.arg2].setText("" + (temp[3] * temp[5] + temp[4] + 1));
                buttons[msg.arg1][msg.arg2].setVisibility(View.VISIBLE);
                buttons[temp[6]][temp[7]].setVisibility(View.INVISIBLE);
            }
        };

        button17.setOnClickListener(new View.OnClickListener() {
            int anotherCount = 0;
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if (button.getText().equals("难度(默认)")) {
                    button.setText("难度(高)");
                }
                else {
                    button.setText("难度(默认)");
                }
                initialize(buttons, button17, dataStore);
            }
        });

        button19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int level;
                if (button17.getText().equals("难度(默认)")) level = 2;
                else level = 3;
                int[][] tempRecords = new int[16][4];
                int dimension = 0;
                int temp = 8;
                if (level == 2) {
                    for (int j = 0; j <= 8; j++) {
                        tempRecords[j][0] = dimension;
                        tempRecords[j][1] = j - 3 * dimension;
                        tempRecords[j][2] = dimension;
                        tempRecords[j][3] = j - 3 * dimension;
                        if ((j+1) % 3 == 0) dimension ++;
                    }
                }
                else {
                    for (int j = 0; j <= 15; j++) {
                        tempRecords[j][0] = dimension;
                        tempRecords[j][1] = j - 4 * dimension;
                        tempRecords[j][2] = dimension;
                        tempRecords[j][3] = j - 4 * dimension;
                        if ((j+1) % 4 == 0) dimension ++;
                    }
                    temp = 15;
                }
                Random random = new Random();
                for (int i = 0; i <= 90; i ++) {
                    int temp0;
                    temp0 = random.nextInt(100);
                    int[] tempRecord = tempRecords[temp];
                    if (temp0 < 50) {
                        if (temp0 < 25) {
                            if (tempRecord[0] - 1 < 0) temp = temp + level + 1;
                            else temp = temp - level - 1;
                        }
                        else {
                            if (tempRecord[0] + 1 > level) temp = temp - level - 1;
                            else temp = temp + level + 1;
                        }
                    }
                    else {
                        if (temp0 < 75) {
                            if (tempRecord[1] - 1 < 0) temp = temp + 1;
                            else temp = temp - 1;
                        }
                        else {
                            if (tempRecord[1] + 1 > level) temp = temp - 1;
                            else temp = temp + 1;
                        }
                    }
                    tempRecord[2] = tempRecords[temp][2];
                    tempRecord[3] = tempRecords[temp][3];
                }
                int tempForBelow;
                if (level == 2) tempForBelow = 9;
                else tempForBelow = 16;
                int[][] records = dataStore.getRecords();
                for (int i = 0; i < tempForBelow; i ++) {
                    int[] tempRecord = tempRecords[i];
                    buttons[tempRecord[0]][tempRecord[1]].setText("" + (1 + tempRecord[3] + tempRecord[2] * (level + 1)));
                    records[i][2] = tempRecord[2];
                    records[i][3] = tempRecord[3];
                    if (i == temp) buttons[tempRecord[0]][tempRecord[1]].setVisibility(View.INVISIBLE);
                    else buttons[tempRecord[0]][tempRecord[1]].setVisibility(View.VISIBLE);
                }
                int[] dot = dataStore.getDot();
                dot[2] = temp;
                dot[0] = tempRecords[temp][0];
                dot[1] = tempRecords[temp][1];
            }
        });

        button18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Button[] buttons1 : buttons) {
                    for (Button button : buttons1) button.setClickable(false);
                }
                button17.setClickable(false);
                button19.setClickable(false);
                button18.setClickable(false);
                int level;
                int circleTimes;
                if (button17.getText().equals("难度(默认)")) {
                    level = 2;
                    circleTimes = 8;
                }
                else {
                    level = 3;
                    circleTimes = 15;
                }
                int[][] tempRecords = dataStore.getRecords();
                int[] tempDot = dataStore.getDot();
                int isSequence = 0;
                for (int i = 0; i < tempRecords.length; i ++) {
                    if (i != tempDot[2]) isSequence += Math.abs(tempRecords[i][0] - tempRecords[i][2]) + Math.abs(tempRecords[i][1] - tempRecords[i][3]);
                }
                if (isSequence == 0) {
                    for (Button[] buttons1 : buttons) {
                        for (Button button : buttons1) button.setClickable(true);
                    }
                    button17.setClickable(true);
                    button19.setClickable(true);
                    button18.setClickable(true);
                    Toast.makeText(MainActivity.this, "Has been serialized", Toast.LENGTH_SHORT).show();
                }
                else {
                    ArrayList<DataForAF> dataForAFS = new ArrayList<>();
                    if (tempDot[0] - 1 >= 0) {
                        int[][] oneRecords = new int[circleTimes + 1][4];
                        for (int i = 0; i <= circleTimes; i ++) oneRecords[i] = tempRecords[i].clone();
                        oneRecords[tempDot[2]][2] = oneRecords[tempDot[2] - level - 1][2];
                        oneRecords[tempDot[2]][3] = oneRecords[tempDot[2] - level - 1][3];
                        int[] tempSteps = {1, 0};
                        int dot = 0;
                        for (int[] i : oneRecords) {
                            if (dot != tempDot[2] - level - 1) {
                                tempSteps[1] += Math.abs(i[0] - i[2]) + Math.abs(i[1] - i[3]);
                            }
                            dot ++;
                        }
                        dataForAFS.add(new DataForAF(oneRecords, tempSteps, new int[] {tempDot[2], tempDot[2] - level - 1}));
                    }
                    if (tempDot[0] + 1 <= level) {
                        int[][] oneRecords = new int[circleTimes + 1][4];
                        for (int i = 0; i <= circleTimes; i ++) oneRecords[i] = tempRecords[i].clone();
                        oneRecords[tempDot[2]][2] = oneRecords[tempDot[2] + level + 1][2];
                        oneRecords[tempDot[2]][3] = oneRecords[tempDot[2] + level + 1][3];
                        int[] tempSteps = {1, 0};
                        int dot = 0;
                        for (int[] i : oneRecords) {
                            if (dot != tempDot[2] + level + 1) {
                                tempSteps[1] += Math.abs(i[0] - i[2]) + Math.abs(i[1] - i[3]);
                            }
                            dot ++;
                        }
                        dataForAFS.add(new DataForAF(oneRecords, tempSteps, new int[] {tempDot[2], tempDot[2] + level + 1}));
                    }
                    if (tempDot[1] - 1 >= 0) {
                        int[][] oneRecords = new int[circleTimes + 1][4];
                        for (int i = 0; i <= circleTimes; i ++) oneRecords[i] = tempRecords[i].clone();
                        oneRecords[tempDot[2]][2] = oneRecords[tempDot[2] - 1][2];
                        oneRecords[tempDot[2]][3] = oneRecords[tempDot[2] - 1][3];
                        int[] tempSteps = {1, 0};
                        int dot = 0;
                        for (int[] i:oneRecords) {
                            if (dot != tempDot[2] - 1) {
                                tempSteps[1] += Math.abs(i[0] - i[2]) + Math.abs(i[1] - i[3]);
                            }
                            dot ++;
                        }
                        dataForAFS.add(new DataForAF(oneRecords, tempSteps, new int[] {tempDot[2], tempDot[2] - 1}));
                    }
                    if (tempDot[1] + 1 <= level) {
                        int[][] oneRecords = new int[circleTimes + 1][4];
                        for (int i = 0; i <= circleTimes; i ++) oneRecords[i] = tempRecords[i].clone();
                        oneRecords[tempDot[2]][2] = oneRecords[tempDot[2] + 1][2];
                        oneRecords[tempDot[2]][3] = oneRecords[tempDot[2] + 1][3];
                        int[] tempSteps = {1, 0};
                        int dot = 0;
                        for (int[] i : oneRecords) {
                            if (dot != tempDot[2] + 1) {
                                tempSteps[1] += Math.abs(i[0] - i[2]) + Math.abs(i[1] - i[3]);
                            }
                            dot ++;
                        }
                        dataForAFS.add(new DataForAF(oneRecords, tempSteps, new int[] {tempDot[2], tempDot[2] + 1}));
                    }
                    int[] shortestPath;
                    while(true) {
                        int tempScore = 150;
                        int recordPosition = 0;
                        int assistRecord = 0;
                        int countStep = 0;
                        for (DataForAF i: dataForAFS) {
                            if ((i.getSteps()[1] + i.getSteps()[0]) <= tempScore) {
                                if (i.getSteps()[0] >= countStep) {
                                    countStep = i.getSteps()[0];
                                    tempScore = i.getSteps()[1] + i.getSteps()[0];
                                    recordPosition = assistRecord;
                                }
                            }
                            assistRecord ++;
                        }
                        if (dataForAFS.get(recordPosition).getSteps()[1] == 0) {
                            shortestPath = dataForAFS.get(recordPosition).getFootprints();
                            break;
                        }
                        int[][] dataRecord = dataForAFS.get(recordPosition).getRecords();
                        int[] dataFootprints = dataForAFS.get(recordPosition).getFootprints();
                        int finalPrint = dataFootprints[dataFootprints.length - 1];
                        int[] dataDot = dataRecord[finalPrint];
                        if (dataDot[0] - 1 >= 0 && finalPrint - level - 1 != dataFootprints[dataFootprints.length - 2]) {
                            int[][] recordsCopy = new int[circleTimes + 1][4];
                            for (int i = 0; i <= circleTimes; i ++) recordsCopy[i] = dataRecord[i].clone();
                            recordsCopy[finalPrint][2] = recordsCopy[finalPrint - level - 1][2];
                            recordsCopy[finalPrint][3] = recordsCopy[finalPrint - level - 1][3];
                            int[] dataSteps = {dataForAFS.get(recordPosition).getSteps()[0] + 1, 0};
                            int dot = 0;
                            for (int[] i : recordsCopy) {
                                if (dot != finalPrint - level - 1) {
                                    dataSteps[1] += Math.abs(i[0] - i[2]) + Math.abs(i[1] - i[3]);
                                }
                                dot ++;
                            }
                            int[] newPrints = Arrays.copyOf(dataFootprints, dataFootprints.length + 1);
                            newPrints[newPrints.length - 1] = finalPrint - level - 1;
                            dataForAFS.add(new DataForAF(recordsCopy, dataSteps, newPrints));
                        }
                        if (dataDot[0] + 1 <= level && finalPrint + level + 1 != dataFootprints[dataFootprints.length - 2]) {
                            int[][] recordsCopy = new int[circleTimes + 1][4];
                            for (int i = 0; i <= circleTimes; i ++) recordsCopy[i] = dataRecord[i].clone();
                            recordsCopy[finalPrint][2] = recordsCopy[finalPrint + level + 1][2];
                            recordsCopy[finalPrint][3] = recordsCopy[finalPrint + level + 1][3];
                            int[] dataSteps = {dataForAFS.get(recordPosition).getSteps()[0] + 1, 0};
                            int dot = 0;
                            for (int[] i:recordsCopy) {
                                if (dot != finalPrint + level + 1) {
                                    dataSteps[1] += Math.abs(i[0] - i[2]) + Math.abs(i[1] - i[3]);
                                }
                                dot ++;
                            }
                            int[] newPrints = Arrays.copyOf(dataFootprints, dataFootprints.length + 1);
                            newPrints[newPrints.length - 1] = finalPrint + level + 1;
                            dataForAFS.add(new DataForAF(recordsCopy, dataSteps, newPrints));
                        }
                        if (dataDot[1] - 1 >= 0 && finalPrint - 1 != dataFootprints[dataFootprints.length - 2]) {
                            int[][] recordsCopy = new int[circleTimes + 1][4];
                            for (int i = 0; i <= circleTimes; i ++) recordsCopy[i] = dataRecord[i].clone();
                            recordsCopy[finalPrint][2] = recordsCopy[finalPrint - 1][2];
                            recordsCopy[finalPrint][3] = recordsCopy[finalPrint - 1][3];
                            int[] dataSteps = {dataForAFS.get(recordPosition).getSteps()[0] + 1, 0};
                            int dot = 0;
                            for (int[] i:recordsCopy) {
                                if (dot != finalPrint - 1) {
                                    dataSteps[1] += Math.abs(i[0] - i[2]) + Math.abs(i[1] - i[3]);
                                }
                                dot ++;
                            }
                            int[] newPrints = Arrays.copyOf(dataFootprints, dataFootprints.length + 1);
                            newPrints[newPrints.length - 1] = finalPrint - 1;
                            dataForAFS.add(new DataForAF(recordsCopy, dataSteps, newPrints));
                        }
                        if (dataDot[1] + 1 <= level && finalPrint + 1 != dataFootprints[dataFootprints.length - 2]) {
                            int[][] recordsCopy = new int[circleTimes + 1][4];
                            for (int i = 0; i <= circleTimes; i ++) recordsCopy[i] = dataRecord[i].clone();
                            recordsCopy[finalPrint][2] = recordsCopy[finalPrint + 1][2];
                            recordsCopy[finalPrint][3] = recordsCopy[finalPrint + 1][3];
                            int[] dataSteps = {dataForAFS.get(recordPosition).getSteps()[0] + 1, 0};
                            int dot = 0;
                            for (int[] i:recordsCopy) {
                                if (dot != finalPrint + 1) {
                                    dataSteps[1] += Math.abs(i[0] - i[2]) + Math.abs(i[1] - i[3]);
                                }
                                dot ++;
                            }
                            int[] newPrints = Arrays.copyOf(dataFootprints, dataFootprints.length + 1);
                            newPrints[newPrints.length - 1] = finalPrint + 1;
                            dataForAFS.add(new DataForAF(recordsCopy, dataSteps, newPrints));
                        }
                        dataForAFS.remove(recordPosition);
                    }
                    Timer timer = new Timer();
                    timer.schedule(new MyTimerTask(level + 1, shortestPath, tempRecords, buttons, handler), 0, 600);
                }
            }
        });
    }

    private void initialize(Button[][] buttons, Button button, DataStore dataStore) {
        int count = 9;
        int anotherCount = 0;
        int[][] records = dataStore.getRecords();
        dataStore.setDot(new int[] {2, 2, 8});

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;

        if (button.getText().equals("难度(默认)")) {
            for (int i = 0; i <= buttons.length - 1; i ++) {
                for (int j = 0; j <= buttons[i].length - 1; j ++) {
                    if (i != 3 && j != 3) {
                        buttons[i][j].setWidth(displayWidth / 3 - 12);
                        buttons[i][j].setHeight(displayWidth / 3 - 12);
                        buttons[i][j].setText("" + (1 + anotherCount));
                        buttons[i][j].setTextSize(40);
                        dataStore.getRecords()[anotherCount][0] = i;
                        dataStore.getRecords()[anotherCount][1] = j;
                        dataStore.getRecords()[anotherCount][2] = i;
                        dataStore.getRecords()[anotherCount][3] = j;
                        buttons[i][j].setOnClickListener(new MyOnClickListener(buttons, button, anotherCount, dataStore));
                        anotherCount ++;
                        if (i == 2 && j == 2) buttons[i][j].setVisibility(View.INVISIBLE);
                        else buttons[i][j].setVisibility(View.VISIBLE);
                    }
                    else {
                        records[count][0] = i;
                        records[count][1] = j;
                        records[count][2] = i;
                        records[count][3] = j;
                        buttons[i][j].setVisibility(View.GONE);
                        buttons[i][j].setOnClickListener(new MyOnClickListener(buttons, button, count, dataStore));
                        count ++;
                    }
                }
            }
        }
        else {
            for (int i = 0; i <= buttons.length - 1; i ++) {
                for (int j = 0; j <= buttons.length - 1; j ++) {
                    anotherCount ++;
                    buttons[i][j].setWidth(displayWidth / 4 - 10);
                    buttons[i][j].setHeight(displayWidth / 4 - 10);
                    buttons[i][j].setTextSize(30);
                    buttons[i][j].setText("" + anotherCount);
                    buttons[i][j].setVisibility(View.VISIBLE);
                    dataStore.getRecords()[anotherCount - 1][0] = i;
                    dataStore.getRecords()[anotherCount - 1][1] = j;
                    dataStore.getRecords()[anotherCount - 1][2] = i;
                    dataStore.getRecords()[anotherCount - 1][3] = j;
                    buttons[i][j].setOnClickListener(new MyOnClickListener(buttons, button, anotherCount - 1, dataStore));
                    if (i == 3 && j == 3) buttons[i][j].setVisibility(View.INVISIBLE);
                }
            }
            dataStore.setDot(new int[] {3, 3, 15});
        }
    }

    private class MyOnClickListener implements View.OnClickListener {
        Button[][] buttons;
        Button button;
        int position;
        DataStore dataStore;
        boolean tempForClick;
        MyOnClickListener(Button[][] buttons, Button button, int position, DataStore dataStore) {
            this.buttons = buttons;
            this.button = button;
            this.dataStore = dataStore;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            int[] temp = dataStore.getDot();
            int[] temp0 = dataStore.getRecords()[position];
            int[] temp1 = dataStore.getRecords()[temp[2]];
            tempForClick = false;
            if ((temp0[0] == temp[0] && Math.abs(temp0[1] - temp[1]) == 1) || (temp0[1] == temp[1] && Math.abs(temp0[0] - temp[0]) == 1)) {
                v.setVisibility(View.INVISIBLE);
                buttons[temp[0]][temp[1]].setVisibility(View.VISIBLE);
                buttons[temp[0]][temp[1]].setText(((Button) v).getText());
                temp1[2] = temp0[2];
                temp1[3] = temp0[3];
                temp[0] = temp0[0];
                temp[1] = temp0[1];
                temp[2] = position;
                tempForClick = true;
            }
            if (tempForClick) {
                int[][] records = dataStore.getRecords();
                for (int i = 0; i <= records.length - 1; i ++) {
                    if ((records[i][0] != records[i][2] || records[i][1] != records[i][3]) && i != temp[2]) break;
                    else if (i == 15) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        dialog.setTitle("Congratulations");
                        dialog.setMessage("You Deserve it!");
                        dialog.setCancelable(true);
                        dialog.setPositiveButton("I got it.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        tempForClick = false;
                    }
                }
            }
        }
    }

    private class DataStore {
        private int[] dot = new int[3];
        private int[][] records = new int[16][4];

        public void setDot(int[] dot) {
            this.dot = dot;
        }

        public int[] getDot() {
            return dot;
        }

        public int[][] getRecords() {
            return records;
        }
    }

    private class DataForAF {
        private int[][] records;
        private int[] steps;
        private int[] footprints;
        DataForAF (int[][] records, int[] steps, int[] footprints) {
            this.records = records;
            this.footprints = footprints;
            this.steps = steps;
        }

        public int[] getFootprints() {
            return footprints;
        }

        public int[] getSteps() {
            return steps;
        }

        public int[][] getRecords() {
            return records;
        }
    }

    private class MyTimerTask extends TimerTask {
        int level;
        int[] stepCount = new int[1];
        int[] steps;
        int[][] records;
        Button[][] buttons;
        Handler handler;
        MyTimerTask(int level, int[] steps, int[][] records, Button[][] buttons, Handler handler) {
            this.level = level;
            this.steps = steps;
            this.records = records;
            this.buttons = buttons;
            this.handler = handler;
            stepCount[0] = 0;
        }

        @Override
        public void run() {
            int[] stepRecord = records[steps[stepCount[0]]];
            Message message = Message.obtain();
            message.arg1 = stepRecord[0];
            message.arg2 = stepRecord[1];
            Bundle bundle = new Bundle();
            bundle.putIntArray("switch", new int[] {steps.length - 2, stepCount[0], steps[stepCount[0]], records[steps[stepCount[0] + 1]][2], records[steps[stepCount[0] + 1]][3], level, records[steps[stepCount[0] + 1]][0], records[steps[stepCount[0] + 1]][1], steps[steps.length - 1]});
            message.setData(bundle);
            handler.sendMessage(message);
            stepCount[0] ++;
            if (stepCount[0] >= steps.length - 1) {
                System.gc();
                cancel();
            }
        }
    }
}