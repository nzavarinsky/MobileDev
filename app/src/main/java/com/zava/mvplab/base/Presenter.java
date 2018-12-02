

package com.zava.mvplab.base;

import android.content.Context;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class Presenter<T extends Presenter.View> {

  private CompositeDisposable compositeDisposable = new CompositeDisposable();

  private T view;

  public T getView() {
    return view;
  }

  public void setView(T view) {
    this.view = view;
  }


  public void terminate() {
    dispose();
  }

  protected void addDisposableObserver(Disposable disposable) {
    compositeDisposable.add(disposable);
  }

  public void dispose() {
    if (!compositeDisposable.isDisposed()) {
      compositeDisposable.dispose();
    }
  }

  public interface View {
    Context context();
  }
}