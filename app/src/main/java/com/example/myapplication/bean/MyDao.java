package com.example.myapplication.bean;

import com.example.myapplication.dao.DaoMaster;
import com.example.myapplication.dao.DaoSession;
import com.example.myapplication.dao.StudentDao;

import java.util.List;

/**
 * Created by 叟 on 2018/12/25.
 */

public class MyDao {

    private final StudentDao studentDao;
    private static MyDao myDao;
    private MyDao(){

        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(MyApp.myApp, "DZL.db");
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();
        studentDao = daoSession.getStudentDao();

    }
    //    单例 ： 防止线程多次创建对象  只保证创建一次
    public static MyDao getInstance(){
        if (myDao == null) {
            synchronized (MyDao.class) {
                if (myDao == null) {
                    myDao = new MyDao();
                }
            }
        }
        return myDao;
    }

    //  插入
    public void insert1(List<Student> studes){
        studentDao.insertInTx(studes); // 插入整个集合
    }
    //  插入
    public void insert2(Student studes){
        studentDao.insert(studes); // 插单个
    }
// ---------------------------------------------------------------------------
    //  删除
    public void delete1(){
        studentDao.deleteAll(); //  删除数据库中的所有数据
    }
    //  删除
    public void delete2(Student student){
        studentDao.delete(student);//  删除单个数据
    }
//---------------------------------------------------------------------------
    //  更新  改
    public void update(Student student){
        studentDao.update(student);
    }
//---------------------------------------------------------------------------
    // 查询
    public List<Student> select(){
        return studentDao.queryBuilder().list(); //  查询全部
    }
    //  条件查询
    public List<Student> selectTiaojian(Student student){
        return studentDao.queryBuilder().where(StudentDao.Properties.Name.eq(student.getName())).list(); //条件查询
    }
//---------------------------------------------------------------------------
    //  分页加载
    public List<Student> selectPage(int page,int count){
        return studentDao.queryBuilder().offset(page*count).limit(count).list();
    }
}