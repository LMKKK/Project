package com.controll;

import com.view.view;

import java.util.Scanner;



import static com.controll.RoleControll.*;

/**
 * @ClassName : Run  //类名
 * @Description : 开始运行  //描述
 * @Author : 刘明凯的专属computer //作者
 * @Date: 2022/12/13 0013  23:32
 */

public class Run {
    public static void run(){
        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        while(true){
            view.firstPage();
            int key;
            key = sc.nextInt();
            switch(key){
                case 1:
                    residentControll();
                    break;
                case 2:
                    managerControll();
                    break;
                case 3:
                    adminControll();
                    break;
                case 4:
                    flag = false;
                    break;
                default:
                    System.out.println("没有这个选项！别乱选");
                    break;
            }
            if(!flag){
                break;
            }
        }


    }
}
