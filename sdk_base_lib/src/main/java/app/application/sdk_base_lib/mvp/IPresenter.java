package app.application.sdk_base_lib.mvp;

public interface IPresenter< T extends IBaseView>{
	public void attachView(T IView);
	public void detachView();
}
