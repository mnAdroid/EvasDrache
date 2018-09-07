package com.me.evasdrache;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

public class MainActivity extends AppCompatActivity {
    //Worker Thread (In unserem Fall macht der quasi alles)
    private GameView gameView;

    //Erster Start der App
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Display Objekt erstellen
        Display display = getWindowManager().getDefaultDisplay();
        //Ergebnis in ein Punkt laden
        Point displaySize = new Point();
        display.getSize(displaySize);

        //Initialisieren der GameView
        gameView = new GameView(this, displaySize.x, displaySize.y);

        //Anzeigen des Spielobjekt gameView auf dem Bildschirm
        setContentView(gameView);
    }

    //Wenn das Spiel (wieder) gestartet wird
    @Override
    protected void onResume() {
        super.onResume();

        gameView.resume();
    }

    //Wenn das Spiel minimiert (oder geschlossen) wird
    @Override
    protected void onPause() {
        super.onPause();

        gameView.pause();
    }
}
