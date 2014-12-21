package Sources;
import java.util.ArrayList;

/**
 * Created by hyeongminpark on 14. 12. 18..
 * 레이저, 토큰의 뱡향은 정수 12, 3, 6, 9 로 표현 되며 각각 12시, 3시, 6시, 9시 방향을 의미한다.
 */

public class Token {
    private int posX;
    private int posY;
    private int dir;
    private boolean moveAble = true;
    private boolean rotateAble = true;

    //레이저가 여러 방향에서 들어오거나 나갈 수 있기에 ArrayList로 저장한다.
    // ==== 레이저 한번 쏜 다음엔 리스트를 비워 줘야 한다!! ====
    private ArrayList<Integer> laserDetectedDirs = new ArrayList<Integer>();
    private ArrayList<Integer> laserShootDirs = new ArrayList<Integer>();

    public Token(){
        this.rotateAble = true;
    }

    public String getColor(){
        return "";
    }



    //
    //// 토큰 위치 관련 메소드
    //

    public void setPosX(int x){

        //고정 토큰인 경우 움직일 수 없다.
        if(this.moveAble == false){
            System.out.println("cannot move fixture tiken");
            return;
        }

        if(x>=0 && x<=5){
            this.posX = x;
            System.out.println("token moved: "+ x);
        }else{
            System.out.println("invalid value: "+ x + " inputted");
        }
    }

    public void setPosY(int y){

        //고정 토큰인 경우 움직일 수 없다.
        if(this.moveAble == false){
            System.out.println("cannot move fixture tiken");
            return;
        }

        if(y>=0 && y<=5){
            this.posY = y;
            System.out.println("token moved: "+ y);
        }else{
            System.out.println("invalid value: " + y + " inputted");
        }
    }


    public int getPosX(){
        return this.posX;
    }

    public int getPosY(){
        return this.posY;
    }



    //
    //// 방향 관련 메소드
    //

    // 12, 3, 6, 9 를 입력으로 받는다.
    public void setDir(int d){
        if(d==12 || d==3 || d==6 || d==9 || d==0) {
            this.dir = d;
        }else{
            System.out.println("invalid value: " + d + " inputted");
        }
    }

    public int getDir(){
        return this.dir;
    }

    // 토큰을 시계방향으로 90도씩 돌린다.
    public void rotate(){

        //회전 불가 토큰인 경우 움직일 수 없다.
        if(this.rotateAble == false){
            System.out.println("this token set unrotatable");
            return;
        }

        switch (this.dir){
            case 12 :
                this.dir = 3;
                break;
            case 3:
                this.dir = 6;
                break;
            case 6:
                this.dir = 9;
                break;
            case 9:
                this.dir = 12;
                break;
            default:
                System.out.println("invalid variable dir value: " + this.dir);
                break;
        }
        System.out.println("rotate: "+ this.dir);
    }



    //
    //// 토큰의 위치가 미리 정해져 움직일수 없는 것인지 혹은 자유롭게 배치 가능한 것인지에 관한 메서드
    //

    public void setMoveAble(boolean ma){
        this.moveAble = ma;
    }

    public boolean getMoveAble(){
        return this.moveAble;
    }



    //
    //// 토큰의 뱡향이 미리 정해져 돌릴 수 없는 것인지 혹은 자유롭게 돌릴 수 있는 것인지에 관한 메서드
    //

    public void setRotateAble(boolean ra){
        this.rotateAble = ra;
    }

    public boolean getRotateAble(){
        return this.rotateAble;
    }



    //
    //// 레이저가 감지된 방향에 관련된 메소드
    //

    public void addLaserDetectedDir(int l){
        if(l==12 || l==3 || l==6 || l==9) {
            this.laserDetectedDirs.add(l);
        }else{
            System.out.println("invalid value: " + l + " inputted");
        }
    }

    public ArrayList<Integer> getLaserDetectedDirs(){
        return this.laserDetectedDirs;
    }

    public void clearLaserDetectedDirs(){
        this.laserDetectedDirs.clear();
    }

    public void setLaserShootDir(){

    }

    public boolean isHit(){return false;}

    public void setHitOrNot(boolean b){}

    //
    //// 레이저를 반사하거나 발사할 방향과 관련된 메소드. 하위 클래스의 setLaserShootDir() 의해 통제된다.
    //

    public void addLaserShootDir(int d){
        if(d==12 || d==3 || d==6 || d==9) {
            this.laserShootDirs.add(d);
        }else{
            System.out.println("invalid value: " + d + " inputted");
        }
    }

    public ArrayList<Integer> getLaserShootDirs(){
        return this.laserShootDirs;
    }

    public void clearLaserShootDirs(){
        this.laserShootDirs.clear();
    }

    public boolean isTarget(){
        return false;
    }


}
