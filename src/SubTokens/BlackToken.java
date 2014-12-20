package SubTokens;

import Sources.Token;

/**
 * Created by hyeongminpark on 14. 12. 18..
 */

public class BlackToken extends Token {
    //기본(12시) 방향 : 필요없음

    private String color = "k";

    public String getColor(){
        return this.color;
    }

    public void setLaserShootDir() {

        for (int laserDetectedDir : this.getLaserDetectedDirs()) {
            switch (laserDetectedDir) {
                case 12:
                    this.addLaserShootDir(6);
                    break;
                case 3:
                    this.addLaserShootDir(9);
                    break;
                case 6:
                    this.addLaserShootDir(12);
                    break;
                case 9:
                    this.addLaserShootDir(3);
                    break;
                default:
                    System.out.println("invalid variable dir value: " + this.getLaserDetectedDirs());
                    break;
            }
        }
    }
}
