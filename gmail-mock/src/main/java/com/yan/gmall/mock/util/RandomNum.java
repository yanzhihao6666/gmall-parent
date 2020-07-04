package com.yan.gmall.mock.util; /**
 * @author : yanzhihao
 * @create: 2020-06-30 17:32
 * @description :
 */
import java.util.Random;

public class RandomNum {
    public static final  int getRandInt(int fromNum,int toNum){
        return   fromNum+ new Random().nextInt(toNum-fromNum+1);
    }
}
