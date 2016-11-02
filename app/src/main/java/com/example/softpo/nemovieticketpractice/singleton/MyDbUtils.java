package com.example.softpo.nemovieticketpractice.singleton;

import android.content.Context;

import org.xutils.DbManager;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;
import org.xutils.x;

/**
 * Created by home on 2016/6/27.
 */
public class MyDbUtils {
    private static MyDbUtils mMyDbUtils;

    public DbManager mDbManager;
    public DbManager.DaoConfig mDaoConfig;

    private MyDbUtils(Context context){
        mDaoConfig = new DbManager.DaoConfig();
        mDaoConfig.setAllowTransaction(true);
        mDaoConfig.setDbName("cinemaData.db");
        mDaoConfig.setDbDir(context.getFilesDir());
        mDaoConfig.setDbVersion(1);
        mDaoConfig.setDbOpenListener(new DbManager.DbOpenListener() {
            @Override
            public void onDbOpened(DbManager db) {

            }
        });

        mDaoConfig.setDbUpgradeListener(new DbManager.DbUpgradeListener() {
            @Override
            public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                try {
                    mDbManager.dropDb();
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
        mDaoConfig.setTableCreateListener(new DbManager.TableCreateListener() {
            @Override
            public void onTableCreated(DbManager db, TableEntity<?> table) {

            }
        });
        mDbManager = x.getDb(mDaoConfig);
    }

    public static MyDbUtils getDbManagerInstance(Context context){
        if (mMyDbUtils == null) {
            synchronized (MyDbUtils.class){
                if (mMyDbUtils == null) {
                    mMyDbUtils = new MyDbUtils(context);
                    return mMyDbUtils;
                }
            }
        }
        return mMyDbUtils;
    }
}
