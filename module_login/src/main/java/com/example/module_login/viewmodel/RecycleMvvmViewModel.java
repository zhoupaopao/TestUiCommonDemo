package com.example.module_login.viewmodel;

import com.example.lib.base.IBaseView;
import com.example.lib.viewmodel.MvvmBaseViewModel;
import com.example.lib_resource.bean.Student;

import java.util.ArrayList;
import java.util.List;

public class RecycleMvvmViewModel extends MvvmBaseViewModel<RecycleMvvmViewModel.CallBack> {
    private String []headUrls={
            "https://dss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3681880960,455182084&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2709866189,4129601854&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1844318833,114428117&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3304919436,1392532584&fm=26&gp=0.jpg",
            "https://dss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3681880960,455182084&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2709866189,4129601854&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1844318833,114428117&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3304919436,1392532584&fm=26&gp=0.jpg",
            "https://dss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3681880960,455182084&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2709866189,4129601854&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1844318833,114428117&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3304919436,1392532584&fm=26&gp=0.jpg","https://dss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3681880960,455182084&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2709866189,4129601854&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1844318833,114428117&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3304919436,1392532584&fm=26&gp=0.jpg"
    };
    List<Student>students=new ArrayList<>();

    public List<Student> getStudents() {
        return students;
    }

    public void getMessage(){
        for(int i=0;i<headUrls.length;i++){
            Student student=new Student(headUrls[i],"小命"+i,"男");
            students.add(student);
        }
        //更新列表可以加上某个固定的参数，数据变化通知更新
        getPageView().listAdapter();

    }

//    public void showDialog(){
//        showLoading(true);
//    }
//    public void addList(){
//        getMessage();
//    }

    public interface CallBack extends IBaseView {
        void listAdapter();
    }
}
