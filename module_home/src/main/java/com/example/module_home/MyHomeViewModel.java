package com.example.module_home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.lib.utils.Utils;
import com.example.lib_resource.bean.MedicalOrderDTO;

public class MyHomeViewModel extends ViewModel {
    private MutableLiveData<String> number;
    private SavedStateHandle handle;
    private MutableLiveData<String> name;
    private String key="NUMBER";
    private String keyBean="MedicalOrderDTO";
    HomeMainModel model=new HomeMainModel();

    public MutableLiveData<String> getNumber() {
        return handle.getLiveData(key);

    }

    public MutableLiveData<MedicalOrderDTO> getDto() {
        return handle.getLiveData(keyBean);

    }
    public MyHomeViewModel(SavedStateHandle handle){
        if(!handle.contains(key)){
            handle.set(key,"0");
        }
        MedicalOrderDTO medicalOrderDTO=new MedicalOrderDTO();
        if(!handle.contains(keyBean)){
            handle.set(keyBean,medicalOrderDTO);
        }
        this.handle=handle;
    }
    public void getName(String nnn){

        model.getNewName(nnn);
    }

    public void add(){
//        handle.set(key,handle.get(key)+"1");
        MedicalOrderDTO nowDTO=((MedicalOrderDTO)handle.get(keyBean));
        nowDTO.setNumber(nowDTO.getNumber()+"1");
        handle.set(keyBean,nowDTO);
    }
    public void addAll(MedicalOrderDTO dd){
        handle.set(keyBean,dd);
    }

    public MutableLiveData<String> getName() {
        return model.getName();
    }
}
