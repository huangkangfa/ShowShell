package app.application.sdk_base_lib.mvp;

public class BasePresenter<T extends IBaseView> implements IPresenter<T> {
	
	protected T mView;
	
	@Override
	public void attachView(T IView) {
		// TODO Auto-generated method stub
		this.mView = IView;
	}
	
	@Override
	public void detachView() {
		// TODO Auto-generated method stub
		this.mView = null;
	}
	
	public boolean isViewAttach(){
		return mView != null;
	}

}
