package Sources;

import SubTokens.*;

import java.util.*;
import java.io.*;

/**
 * Created by hyeongminpark on 14. 12. 18..
 */



public class Card {
    private int cardNum; // 카드번호
    private ArrayList<Token> freeTokens = new ArrayList<Token>(); // free 토큰
    private Token[] freeTokenTable = new Token[5]; // 게임을 진행할 free토큰 라인
    private int numOfTargets; // 타겟 개수
    private ArrayList<Token> fixtureTokens = new ArrayList<Token>(); // fixture 토큰
    private Token[][] tokenTable = new Token[5][5]; // 게임을 진행할 토큰테이블

    //
    //// Card 객체 제너레이터
    //
    public Card(String cardFileName){

        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                tokenTable[i][j]=null;
            }
        }

        BufferedReader br = null; // 라인 읽어들일 버퍼
        String line = null; // 한줄씩 저장할 변수

        try{
            //파일 전체를 bufferedReader에 저장한다.
            br = new BufferedReader(new FileReader(cardFileName));

            //한줄씩 읽으며 적절한 변수에 적절한 객체를 넣어준다
            while((line = br.readLine()) != null){
                if(line.length() < 1)
                    continue;
                String[] sArr = line.split(" ");
                if(sArr.length < 3)
                    continue;

                // 특정 키워드
                if(sArr[0].equals("cardNum")) // 카드번호 저장
                    this.readCardNum(sArr);
                else if(sArr[0].equals("freeTokens")) // 자유토큰 추가
                    this.readFreeToken(sArr);
                else if(sArr[0].equals("numOfTargets")) // 타겟개수 저장
                    this.readNumOfTargets(sArr);
                else if(sArr[0].equals("fixtureTokens")) // 고정토큰 추가
                    this.readFixtureTokens(sArr);
            }

            for(int i=0; i<freeTokens.size(); i++){
                freeTokenTable[i]=freeTokens.get(i);
            }

            // 고정 토큰 리스트를 참조하여 tokenTable에 추가한다.
            for(int i=0; i<fixtureTokens.size(); i++){
                tokenTable[fixtureTokens.get(i).getPosY()][fixtureTokens.get(i).getPosX()] = fixtureTokens.get(i);
            }
        }catch (IOException ex){
            //에러 발생시 로그 던짐.
            ex.printStackTrace();
        }

    }

    public Token[] getFreeTokenTable(){
        return freeTokenTable;
    }

    public Token[][] getTokenTable(){
        return tokenTable;
    }

    public void insertFreeToknToTable(Token token, int x, int y){
        tokenTable[y][x]=token;
    }

    public void removeTokenFromLine(int x){
        freeTokenTable[x]=null;
    };

    public void removeTokenfromTable(int x, int y){
        tokenTable[y][x] = null;
    }

    //카드 번호를 읽어와서 cardNum에 저장
    private void readCardNum(String[] arr){
        String cardNumberVal = arr[2];
        this.cardNum = Integer.parseInt(cardNumberVal);
    }

    // 자유 토큰을 읽어와서 freeTokens에 추가
    private void readFreeToken(String[] arr){
        String colorVal = arr[2];
        String isTargetVal = arr[3];
        freeTokens.add(makeFreeToken(colorVal, isTargetVal));
    }

    // 타겟 개수를 읽어와서 numOfTargets에 추가
    private void readNumOfTargets(String[] arr){
        String numOfTargetsVal = arr[2];
        this.numOfTargets = Integer.parseInt(numOfTargetsVal);
    }

    // 고정 토큰을 읽어와서 fixtureTokens에 추가
    private void readFixtureTokens(String[] arr){
        String colorVal = arr[2];
        String rotateVal = arr[3];
        String posXVal = arr[4];
        String posYVal = arr[5];
        String isTargetVal = arr[6];
        String dirVal = arr[7];
        fixtureTokens.add(makeFixtureToken(colorVal, rotateVal, posXVal, posYVal, isTargetVal, dirVal));
    }

    // 자유토큰 정보를 받아와서 자유토큰 객체를 만들어 반환한다.
    private Token makeFreeToken(String color, String isTarget){

        Token newFreeToken =  new Token();

        if(color.equals("k")){
            BlackToken newBlackToken = new BlackToken();
            newFreeToken = newBlackToken;

        }else if(color.equals("b")){
            BlueToken newBlueToken = new BlueToken();
            newFreeToken = newBlueToken;

        }else if(color.equals("g")){
            GreenToken newGreenToken = new GreenToken();
            newFreeToken = newGreenToken;

        }else if(color.equals("p")){
            PurpleToken newPurpleToken =new PurpleToken();

            //이동 가능한 보라색 토큰이 표적일 경우, 표적 표시를 해준다.
            if(isTarget.equals("y")){
                newPurpleToken.setTarget();
            }

            newFreeToken = newPurpleToken;

        }else if(color.equals("r")){
            RedToken newRedToken = new RedToken();
            newFreeToken = newRedToken;

        }else if(color.equals("y")){
            YellowToken newYellowToken = new YellowToken();
            newFreeToken = newYellowToken;

        }else{
            System.out.println("===== wrong color key inputted: "+ color);
        }

        //free 토큰이기때문에 움직일수 있도록 한다
        newFreeToken.setMoveAble(true);

        //회전 가능하게 설정
        newFreeToken.setRotateAble(true);
        newFreeToken.setDir(12);

        return newFreeToken;
    }

    // 고정토큰 정보를 받아와서 고정토큰 정보를 반환한다.
    private Token makeFixtureToken(String color, String rotate, String posX, String posY, String isTarget, String dir){

        Token newFreeToken =  new Token();

        if(color.equals("k")){
            BlackToken newBlackToken = new BlackToken();
            newFreeToken = newBlackToken;

        }else if(color.equals("b")){
            BlueToken newBlueToken = new BlueToken();
            newFreeToken = newBlueToken;

        }else if(color.equals("g")){
            GreenToken newGreenToken = new GreenToken();
            newFreeToken = newGreenToken;

        }else if(color.equals("p")){
            PurpleToken newPurpleToken =new PurpleToken();

            //이동 가능한 보라색 토큰이 표적일 경우, 표적 표시를 해준다.
            if(isTarget.equals("y")){
                newPurpleToken.setTarget();
            }

            newFreeToken = newPurpleToken;

        }else if(color.equals("r")){
            RedToken newRedToken = new RedToken();
            newFreeToken = newRedToken;

        }else if(color.equals("y")){
            YellowToken newYellowToken = new YellowToken();
            newFreeToken = newYellowToken;

        }else{
            System.out.println("===== wrong color key inputted: "+ color);
        }

        // 위치를 미리 지정한다
        newFreeToken.setPosX(Integer.parseInt(posX));
        newFreeToken.setPosY(Integer.parseInt(posY));

        //고정된 토큰이기때문에 움직일수 없도록 한다
        newFreeToken.setMoveAble(false);

        //회전 가능 / 불가
        if(rotate.equals("r")){ //위치는 고정이지만 회전 가능한 경우
            newFreeToken.setRotateAble(true);
            newFreeToken.setDir(12);
        }else if(rotate.equals("u")){ //위치도 고정 회전도 불가인 경우
            newFreeToken.setRotateAble(false);
            newFreeToken.setDir(Integer.parseInt(dir));
        }

        return newFreeToken;
    }
    private int count = 1;
    public void plotBoard(){
        for(int i=0; i<5; i++){
            if(freeTokenTable[i]==null){
                System.out.print("X");
            }else{
                System.out.print(freeTokenTable[i].getColor());
            }
        }
        System.out.println("\n=====");
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                if(tokenTable[i][j]==null){
                    System.out.print("X");
                }else{
                    System.out.print(tokenTable[i][j].getColor());
                }
            }
            System.out.print("\n");
        }
        System.out.println("count: " + count);
        count++;
    }

    ArrayList<LaserGoing> laserGoingList = new ArrayList<LaserGoing>();

    class LaserGoing{
        public int laserDir;
        public int fromX;
        public int fromY;

        public LaserGoing(int x,int y,int d){
            this.laserDir = d;
            this.fromX = x;
            this.fromY = y;
        }
    }

    ArrayList<LaserGoing> laserGoingFullPath = new ArrayList<LaserGoing>();
    boolean isWin = false;

    public void shootLaser(){
        laserGoingFullPath.clear();

        //// 빨간토큰 찾아서 레이저 정보 추가한다.
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                if(tokenTable[j][i]==null){
                    continue;
                }
                if(tokenTable[j][i].getColor().equals("r")){
                    System.out.println("빨간토큰 찾음!: " + i + " "+ j);
                    tokenTable[j][i].setLaserShootDir();
                    laserGoingList.add(new LaserGoing(i, j,
                            tokenTable[j][i].getLaserShootDirs().get(tokenTable[j][i].getLaserShootDirs().size()-1)));
                    System.out.println("레이저 진행방향 "+tokenTable[j][i].getLaserShootDirs().get(tokenTable[j][i].getLaserShootDirs().size()-1));
                }
            }
        }
        while(laserGoingList.size()>0){
            for(int k=0; k<laserGoingList.size(); k++){
                for(int i=0; i<5; i++){
                    for(int j=0; j<5; j++){
                        if(laserGoingList.get(k).fromX==j && laserGoingList.get(k).fromY==i){
                            System.out.print("O");
                        }else{
                            System.out.print("X");
                        }
                    }
                    System.out.print("\n");
                }
                System.out.println("======");
            }
            reflectLaser();
        }

        //모든 칸칸마다 초기화
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                if(tokenTable[i][j]==null){
                    continue;
                }
                tokenTable[i][j].clearLaserDetectedDirs();
                tokenTable[i][j].clearLaserShootDirs();
            }
        }


    }


    int reflectCount;

    public void reflectLaser(){
        reflectCount++;

        ArrayList<LaserGoing> tmpLaserGoingList = new ArrayList<LaserGoing>();

        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                if(tokenTable[i][j]==null){
                    continue;
                }
                tokenTable[i][j].clearLaserDetectedDirs();
                tokenTable[i][j].clearLaserShootDirs();
            }
        }

        //현재 시점의 각각의 레이저 경로(경로 여러개일때 대비하여) 에 대해 순환문
        for(int i=0; i<laserGoingList.size(); i++){

            if(laserGoingList.get(i).laserDir==3){
                //레이저가 3시방향을 향할때

                int y = laserGoingList.get(i).fromY;

                //레이저 방향대로 따라가며 토큰이 있는지 탐색
                for(int x = laserGoingList.get(i).fromX+1; x<5; x++){
                    if(tokenTable[y][x]!=null){
                        tokenTable[y][x].addLaserDetectedDir(9);
                        tokenTable[y][x].setLaserShootDir();

                        System.out.println("\n==레이저 경로상 발결된 토큰==");
                        System.out.println("color is: "+ tokenTable[y][x].getColor());
                        System.out.println("좌표: x: "+x + " y: "+ y);
                        System.out.println("hit?: "+ tokenTable[y][x].isHit());
                        System.out.println("reflect setting → ..");
                        System.out.println("==이까지==\n");

                    }
                }

            }else if(laserGoingList.get(i).laserDir==6){
                //레이저가 6시방향을 향할때

                int x = laserGoingList.get(i).fromX;

                //레이저 방향대로 따라가며 토큰이 있는지 탐색
                for(int y = laserGoingList.get(i).fromY+1; y<5; y++){
                    if(tokenTable[y][x]!=null){
                        tokenTable[y][x].addLaserDetectedDir(12);
                        tokenTable[y][x].setLaserShootDir();

                        System.out.println("\n==레이저 경로상 발결된 토큰==");
                        System.out.println("color is: "+ tokenTable[y][x].getColor());
                        System.out.println("좌표: x: "+x + " y: "+ y);
                        System.out.println("hit?: "+ tokenTable[y][x].isHit());
                        System.out.println("reflect setting ↓ ..");
                        System.out.println("==이까지==\n");


                    }
                }

            }else if(laserGoingList.get(i).laserDir==9){
                //레이저가 9시방향을 향할때

                int y = laserGoingList.get(i).fromY;

                //레이저 방향대로 따라가며 토큰이 있는지 탐색
                for(int x = laserGoingList.get(i).fromX-1; x>=0; x--){
                    if(tokenTable[y][x]!=null){
                        tokenTable[y][x].addLaserDetectedDir(3);
                        tokenTable[y][x].setLaserShootDir();

                        System.out.println("\n==레이저 경로상 발결된 토큰==");
                        System.out.println("color is: "+ tokenTable[y][x].getColor());
                        System.out.println("좌표: x: "+x + " y: "+ y);
                        System.out.println("hit?: "+ tokenTable[y][x].isHit());
                        System.out.println("reflect setting → ..");
                        System.out.println("==이까지==\n");


                    }
                }

            }else if(laserGoingList.get(i).laserDir==12){
                //레이저가 12시방향을 향할때

                int x = laserGoingList.get(i).fromX;

                //레이저 방향대로 따라가며 토큰이 있는지 탐색
                for(int y = laserGoingList.get(i).fromY-1; y>=0; y--){
                    if(tokenTable[y][x]!=null){
                        tokenTable[y][x].addLaserDetectedDir(6);
                        tokenTable[y][x].setLaserShootDir();

                        System.out.println("\n==레이저 경로상 발결된 토큰==");
                        System.out.println("color is: " + tokenTable[y][x].getColor());
                        System.out.println("좌표: x: "+x + " y: "+ y);
                        System.out.println("hit?: "+ tokenTable[y][x].isHit());
                        System.out.println("reflect setting ↑ ..");
                        System.out.println("==이까지==\n");


                    }
                }

            }else{
                System.out.println("이상한 레이저 사입각: " + laserGoingList.get(i).laserDir);
            }


        }

        int hitCount = 0;

        //모든 칸칸마다
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){

                //만약 토큰이 있다면
                if(tokenTable[j][i]!=null){
                    LaserGoing tmpLaserGoing;

                    if(tokenTable[j][i].isTarget()){
                        System.out.println("found target");
                    }
                    if(tokenTable[j][i].isHit()){
                        hitCount++;
                        System.out.println("=====Hit "+hitCount+" target(s)====");
                        System.out.println("hitToken: "+i+" "+j);

                        if(hitCount == numOfTargets){
                            System.out.println("================");
                            System.out.println("====== WIN =====");
                            System.out.println("================");
                            isWin = true;
                        }
                    }

                    //토큰이 레이저를 보낼 모든 방향으로 새로운 laserGoing 설정
                    for (int d : tokenTable[j][i].getLaserShootDirs()){
                        tmpLaserGoing = new LaserGoing(i, j, d);
                        tmpLaserGoingList.add(tmpLaserGoing);
                    }
                }
            }
        }

        laserGoingFullPath.addAll(laserGoingList);
        laserGoingList.clear();
        for(int i=0; i<tmpLaserGoingList.size(); i++){
            laserGoingList.add(tmpLaserGoingList.get(i));
        }
    }
}
