package com.me.evasdrache;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable{

    //Context von MainActivity
    private Context fullContext;

    //Display Size
    private int screenX, screenY;

    //Standard Canvas und Paint Objekte (zum Malen)
    private Canvas canvas;
    private Paint paint;

    //SurfaceHolder reserviert den Bildschirm und niemand
    //anders darf solange darauf malen
    //Braucht man zum Beispiel um mit Threads zu malen
    private SurfaceHolder surfaceHolder;

    //Boolean ob Spiel gerade läuft oder pausiert ist
    //volatile ist ein synchronized ohne Overhead
    private volatile boolean isOpen;

    //Boolean um zu sehen ob alle Ressourcen geladen sind
    private boolean loaded;

    //Thread der unser Gameloop ist
    private Thread gameThread;

    //FPS
    //Um gleichmäßige Animationen zu haben
    private long fps;

    //Konstruktor
    GameView(Context context, int screenX, int screenY) {
        super(context);
        //Default Variablenbelegung
        gameThread = null;
        loaded = false;

        //Context abspeichern
        fullContext = context;

        //Bildschirmkoordinaten
        this.screenX = screenX;
        this.screenY = screenY;

        //Initialisierung der Zeichen Objekte
        surfaceHolder = getHolder();
        paint = new Paint();
        canvas = new Canvas();
    }

    //run() ist quasi eine Endlosschleife
    @Override
    public void run() {
        //Start des Frames wird gespeichert
        long startFrameTime = System.currentTimeMillis();

        //Alles wird gezeichnet
        draw();

        //Alle Berechnungen werden durchgeführt
        update();

        //FPS Berechnung
        long timeThisFrame = System.currentTimeMillis() - startFrameTime;
        if (timeThisFrame > 0)
            fps = 1000/timeThisFrame;
    }

    void resume() {
        isOpen = true;

        //TODO gespeicherte Variablen auslesen

        //GameThread starten (this = gameView)
        gameThread = new Thread(this);
        gameThread.start();

        //TODO Laden der Grafiken und Sounds

        //Spiel geht los wenn alles geladen ist
        loaded = true;
    }

    void pause() {
        isOpen = false;
        loaded = false;

        //TODO Variablen abspeichern

        //TODO Garbagecollector helfen

        //GameThread beenden
        try {
            gameThread.join();
        } catch(InterruptedException e) {
            Log.e("ThreadError", "Joining Thread " + e);
        }
    }

    //Alle Zeichnungen der App "Machen"
    void draw() {
        //Standardfehler abfangen
        if (surfaceHolder.getSurface().isValid()) {
            try {
                //Canvas wird Zeichenobjekt
                canvas = surfaceHolder.lockCanvas();
            } catch (IllegalArgumentException e) {
                surfaceHolder.unlockCanvasAndPost(canvas);
                Log.e("DrawError", "Canvas Log failed ", e);
            }
            //Tatsächliches Zeichnen
            try {

            } catch(NullPointerException e) {
                //Da es bedeutet, dass eine Grafik nicht eingelesen wurde
                //Lesen wir einfach neu ein
                //TODO initialiseGrafics();
                Log.e("DrawError", "NullPointer at drawing " + e.toString());
            }
        }
    }

    //Alle Berechnungen der App "Denken"
    void update() {

    }
}
