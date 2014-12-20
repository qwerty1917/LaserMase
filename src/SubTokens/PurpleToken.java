package SubTokens;

import Sources.Token;

/**
 * Created by hyeongminpark on 14. 12. 18..
 */

public class PurpleToken extends Token {
    //기본(12시) 방향 : ↖ 모양 (검은부분이 위쪽)

    private String color = "p";

    public String getColor(){
        return this.color;
    }

    private boolean isTarget = false;

    public void setLaserShootDir() {

        for(int laserDetectedDir : this.getLaserDetectedDirs()){
            if(getDir() == 12){
                switch (laserDetectedDir) {
                    case 12:
                        break;
                    case 3:
                        break;
                    case 6:
                        this.addLaserShootDir(9);
                        break;
                    case 9:
                        this.addLaserShootDir(6);
                        break;
                    default:
                        System.out.println("invalid variable dir value: " + this.getLaserDetectedDirs());
                        break;
                }
            }else if(getDir() == 3){
                switch (laserDetectedDir) {
                    case 12:
                        this.addLaserShootDir(9);
                        break;
                    case 3:
                        break;
                    case 6:
                        break;
                    case 9:
                        this.addLaserShootDir(12);
                        break;
                    default:
                        System.out.println("invalid variable dir value: " + this.getLaserDetectedDirs());
                        break;
                }
            }else if(getDir() == 6){
                switch (laserDetectedDir) {
                    case 12:
                        this.addLaserShootDir(3);
                        break;
                    case 3:
                        this.addLaserShootDir(12);
                        break;
                    case 6:
                        break;
                    case 9:
                        break;
                    default:
                        System.out.println("invalid variable dir value: " + this.getLaserDetectedDirs());
                        break;
                }
            }else if(getDir() == 9){
                switch (laserDetectedDir) {
                    case 12:
                        break;
                    case 3:
                        this.addLaserShootDir(6);
                        break;
                    case 6:
                        this.addLaserShootDir(3);
                        break;
                    case 9:
                        break;
                    default:
                        System.out.println("invalid variable dir value: " + this.getLaserDetectedDirs());
                        break;
                }
            }else{
                System.out.println("invalid variable dir value: " + this.getDir());
            }
        }
    }



    //
    //// 타겟이 토큰의 어느 방향에 붙어있는지 알려주는 메서드
    //

    public int getTargetDir(){
        switch (this.getDir()){
            case 12:
                return 12;
            case 3:
                return 3;
            case 6:
                return 6;
            case 9:
                return 9;
            default:
                System.out.println("invalid variable dir value: " + this.getLaserDetectedDirs());
                return 0;
        }
    }



    //
    //// 해당 보라색 토큰을 타겟으로 설정하고, 타겟으로 설정되었는지 확인하고, 레이저에 맞았는지 확인하는 메서드들
    //

    public void setTarget(){
        isTarget = true;
    }

    public boolean isTarget(){
        return isTarget;
    }

    public boolean isHit(){
        if(this.getLaserDetectedDirs().contains(this.getTargetDir()) && this.isTarget()){
            return true;
        }else{
            return false;
        }
    }
}
