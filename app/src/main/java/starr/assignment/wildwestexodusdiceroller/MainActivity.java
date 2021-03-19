package starr.assignment.wildwestexodusdiceroller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class MainActivity extends AppCompatActivity{
    private static final int MAX_DICE_COUNT = 20;
    DiceAdapter diceAdapter;
    List <Dice> diceList = new ArrayList<>();

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup for ListView
    ListView listView = findViewById(R.id.dice_list);
    diceAdapter = new DiceAdapter(this, R.layout.dice_row, diceList);
    listView.setAdapter(diceAdapter);

    //Initialize Data
    diceAdapter.add(new Dice());
    }
    public void addDice(View view) {
        if(diceList.size()< MAX_DICE_COUNT) {
            diceAdapter.add(new Dice());
        }
    }
    public void removeDice(View view) {
    if(!diceList.isEmpty()){
        int lastIndex = diceList.size() - 1;
        diceAdapter.remove(diceAdapter.getItem(lastIndex));
    }
    }
    public void rollDice(View view) {
    //roll all dice
        for(Dice dice :diceList) {
            if(!dice.hold)
                dice.roll();
        }
        //notify adapter to update view
        diceAdapter.notifyDataSetChanged();
    }
    public class DiceAdapter extends ArrayAdapter<Dice> {
    public DiceAdapter(@NonNull Context context, int resource, List<Dice> list){
        super(context, resource, list);
    }
    @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dice_row, parent, false);
        }
        //dice image setup
        ImageView imageView = convertView.findViewById(R.id.dice_icon);
        Dice dice = diceAdapter.getItem(position);
        switch  (dice.diceVal) {
            case DICE1:
                imageView.setImageResource((R.drawable.dice1));
                break;
            case DICE2:
                imageView.setImageResource(R.drawable.dice2);
                break;
            case DICE3:
                imageView.setImageResource(R.drawable.dice3);
                break;
            case DICE4:
                imageView.setImageResource(R.drawable.dice4);
                break;
            case DICE5:
                imageView.setImageResource(R.drawable.dice5);
                break;
            case DICE6:
                imageView.setImageResource(R.drawable.dice6);
                break;
            case DICE7:
                imageView.setImageResource(R.drawable.dice7);
                break;
            case DICE8:
                imageView.setImageResource(R.drawable.dice8);
                break;
            case DICE9:
                imageView.setImageResource(R.drawable.dice9);
                break;
            case DICE10:
                imageView.setImageResource((R.drawable.dice10));
                break;
        }
        //dice hold button
        Button holdButton = convertView.findViewById(R.id.dice_hold_button);
        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dice dice = diceList.get(position);
                dice.toggleHold();
            }
        });
        //dice refine button
        Button changeButton = convertView.findViewById(R.id.dice_change_button);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dice dice = diceList.get(position);
                dice.nextValue();
                diceAdapter.notifyDataSetChanged();
            }
        });
        return convertView;
    }
    }

    public static class Dice {
    public static int SIDES = 10;
    public enum Face {
        DICE1,
        DICE2,
        DICE3,
        DICE4,
        DICE5,
        DICE6,
        DICE7,
        DICE8,
        DICE9,
        DICE10
    }
    public static Random random = new Random();

    boolean hold = false;
    Face diceVal;

    Dice() {
        roll();
    }

        private void roll() {
        int num = random.nextInt(11);
        if(num==0) {
            this.diceVal = Face.DICE1;
        } else {
            if(random.nextBoolean()) {
                this.diceVal = Face.DICE2;
            } else if (random.nextBoolean()){
                this.diceVal = Face.DICE3;
            }else if (random.nextBoolean()) {
                this.diceVal = Face.DICE4;
            }else if (random.nextBoolean()) {
                this.diceVal = Face.DICE5;
            }else if (random.nextBoolean()) {
                this.diceVal = Face.DICE6;
            }else if (random.nextBoolean()) {
                this.diceVal = Face.DICE7;
            }else if (random.nextBoolean()) {
                this.diceVal = Face.DICE8;
            }else if (random.nextBoolean()){
                this.diceVal = Face.DICE9;
            }else {
                this.diceVal = Face.DICE10;
            }
        }
        }
        public void toggleHold() {
            hold = !hold;
        }
        public void nextValue(){
            int index = diceVal.ordinal();
            index = (index+1) % Face.values().length;
            diceVal = Face.values()[index];
        }
    }
}
