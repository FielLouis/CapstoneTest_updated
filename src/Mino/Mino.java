package Mino;

import Tetris.TetrisKeyHandler;
import Tetris.TetrisPlayManager;

import java.awt.*;

public class Mino {
    public Block[] b = new Block[4];
    public Block[] tempB = new Block[4];

    int autoDropCounter = 0;
    public int direction = 1;
    boolean leftColl,rightColl,botColl;
    public boolean active = true;

    public boolean deactivating;
    int deactivateCounter = 0;


    public void create(Color c){
        b[0] = new Block(c);
        b[1] = new Block(c);
        b[2] = new Block(c);
        b[3] = new Block(c);
        tempB[0] = new Block(c);
        tempB[1] = new Block(c);
        tempB[2] = new Block(c);
        tempB[3] = new Block(c);
    }
    public void setXY(int x, int y){

    }
    public  void updateXY(int direction){
        checkRotationCollision();

        if(!leftColl && !rightColl && !botColl) {
            this.direction = direction;
            b[0].x = tempB[0].x;
            b[0].y = tempB[0].y;
            b[1].x = tempB[1].x;
            b[1].y = tempB[1].y;
            b[2].x = tempB[2].x;
            b[2].y = tempB[2].y;
            b[3].x = tempB[3].x;
            b[3].y = tempB[3].y;
        }
    }
    public void getDirection1(){}
    public void getDirection2(){}
    public void getDirection3(){}
    public void getDirection4(){}

    public void checkMovementCollision(){
        leftColl = false;
        rightColl = false;
        botColl = false;
        checkStaticBlockCollision();

        //check frame coll, left wall;
        for(int i = 0; i < b.length; i++){
            if(b[i].x == TetrisPlayManager.left_x){
                leftColl = true;
            }
        }

        //rightwall
        for(int i = 0; i < b.length; i++){
            if(b[i].x + Block.SIZE == TetrisPlayManager.right_x){
                rightColl = true;
            }
        }

        //bottfloor
        for(int i = 0; i<b.length;i++){
            if(b[i].y + Block.SIZE == TetrisPlayManager.bot_y){
                botColl=true;
            }
        }
    }

    public void checkRotationCollision(){
        leftColl = false;
        rightColl = false;
        botColl = false;

        checkStaticBlockCollision();

        //check frame coll, left wall;
        for(int i = 0; i < b.length; i++){
            if(tempB[i].x < TetrisPlayManager.left_x){
                leftColl = true;
            }
        }
        //rightwall
        for(int i = 0; i < b.length; i++){
            if(tempB[i].x + Block.SIZE > TetrisPlayManager.right_x){
                rightColl = true;
            }
        }

        //bottfloor
        for(int i = 0; i<b.length;i++){
            if(tempB[i].y + Block.SIZE > TetrisPlayManager.bot_y){
                botColl=true;
            }
        }
    }
    public void checkStaticBlockCollision(){
        for(int i = 0;i< TetrisPlayManager.staticBlocks.size();i++){
            int targetX = TetrisPlayManager.staticBlocks.get(i).x;
            int targetY = TetrisPlayManager.staticBlocks.get(i).y;

            //down
            for(int j = 0; j < b.length;j++){
                if(b[j].y + Block.SIZE == targetY && b[j].x == targetX){
                    botColl= true;
                }
            }
            //left
            for(int j = 0; j < b.length;j++){
                if(b[j].x - Block.SIZE == targetX && b[j].y == targetY){
                    leftColl= true;
                }
            }
            //right
            for(int j = 0; j < b.length;j++){
                if(b[j].x + Block.SIZE == targetX && b[j].y == targetY){
                    rightColl= true;
                }
            }


        }
    }
    public void update(){
        if(deactivating){
            deactivating();
        }
        if(TetrisKeyHandler.upPressed){
            switch (direction) {
                case 1 -> getDirection2();
                case 2 -> getDirection3();
                case 3 -> getDirection4();
                case 4 -> getDirection1();
            }
            TetrisKeyHandler.upPressed = false;
        }
        checkMovementCollision();

        if (TetrisKeyHandler.downPressed) {
            if (!botColl) {
                b[0].y += Block.SIZE;
                b[1].y += Block.SIZE;
                b[2].y += Block.SIZE;
                b[3].y += Block.SIZE;

                autoDropCounter = 0;
            }
            TetrisKeyHandler.downPressed = false;
        }

        if(TetrisKeyHandler.leftPressed){
            if(!leftColl) {
                b[0].x -= Block.SIZE;
                b[1].x -= Block.SIZE;
                b[2].x -= Block.SIZE;
                b[3].x -= Block.SIZE;
            }
            TetrisKeyHandler.leftPressed = false;
        }
        if(TetrisKeyHandler.rightPressed){
            if(!rightColl) {
                b[0].x += Block.SIZE;
                b[1].x += Block.SIZE;
                b[2].x += Block.SIZE;
                b[3].x += Block.SIZE;
            }
            TetrisKeyHandler.rightPressed = false;
        }
        if (botColl) {
            deactivating = true;
        } else {
            autoDropCounter++;
            if (autoDropCounter == TetrisPlayManager.dropInterval) {
                b[0].y += Block.SIZE;
                b[1].y += Block.SIZE;
                b[2].y += Block.SIZE;
                b[3].y += Block.SIZE;
                autoDropCounter = 0;
            }
        }
    }

    public void deactivating(){
        deactivateCounter++;

        if(deactivateCounter == 45){
            deactivateCounter = 0;
            checkMovementCollision();

            if(botColl){
                active = false;
            }
        }
    }
    public void draw(Graphics2D g2){
        int margin = 2;
        g2.setColor(b[0].c);
        g2.fillRect(b[0].x+margin, b[0].y+margin, Block.SIZE -(margin*2), Block.SIZE-(margin*2));
        g2.fillRect(b[1].x+margin, b[1].y+margin, Block.SIZE-(margin*2), Block.SIZE-(margin*2));
        g2.fillRect(b[2].x+margin, b[2].y+margin, Block.SIZE-(margin*2), Block.SIZE-(margin*2));
        g2.fillRect(b[3].x+margin, b[3].y+margin, Block.SIZE-(margin*2), Block.SIZE-(margin*2));
    }
}
