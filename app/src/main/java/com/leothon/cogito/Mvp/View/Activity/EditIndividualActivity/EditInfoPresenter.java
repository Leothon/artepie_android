package com.leothon.cogito.Mvp.View.Activity.EditIndividualActivity;

public class EditInfoPresenter implements EditInfoContract.IEditInfoPresenter,EditInfoContract.OnEditInfoFinishedListener {

    private EditInfoContract.IEditInfoView iEditInfoView;
    private EditInfoContract.IEditInfoModel iEditInfoModel;

    public EditInfoPresenter(EditInfoContract.IEditInfoView iEditInfoView){
        this.iEditInfoView = iEditInfoView;
        this.iEditInfoModel = new EditInfoModel();
    }
    @Override
    public void getIconUrl(String url) {
        if (iEditInfoView != null){
            iEditInfoView.getIconUrl(url);
        }
    }

    @Override
    public void showMsg(String msg) {
        if (iEditInfoView != null){
            iEditInfoView.showMsg(msg);
        }
    }

    @Override
    public void updateSucess() {
        if (iEditInfoView != null){
            iEditInfoView.updateSuccess();
        }
    }

    @Override
    public void onDestory() {
        iEditInfoView = null;
    }

    @Override
    public void updateUserIcon(String path) {
        iEditInfoModel.uploadIcon(path,this);
    }

    @Override
    public void updateUserInfo(String icon, String name, int sex, String birth, String phone, String signal, String address) {
        iEditInfoModel.uploadAllInfo(icon,name,sex,birth,phone,signal,address,this);
    }
}
