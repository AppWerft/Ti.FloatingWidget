/**
 * This file was auto-generated by the Titanium Module SDK helper for Android
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2017 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package ti.floatingwidget;

import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

// This proxy can be created by calling Floatingwidget.createExample({message: "hello world"})
@Kroll.proxy(creatableInModule = FloatingwidgetModule.class)
public class WidgetProxy extends KrollProxy {
	// Standard Debugging variables
	private static final String LCAT = "TiFloater";
	final WindowManager.LayoutParams params;
	Context ctx;
	private WindowManager windowManager;
	private View nativeView;

	// Constructor
	public WidgetProxy() {
		super();
		ctx = TiApplication.getAppRootOrCurrentActivity().getApplicationContext();
		int LAYOUT_TYPE = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
				? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
				: WindowManager.LayoutParams.TYPE_PHONE;
		params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT, LAYOUT_TYPE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);
		windowManager = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
	}

	@Kroll.method
	public void destroy() {
		if (nativeView != null)
			windowManager.removeView(nativeView);
	}

	@Override
	public void handleCreationArgs(KrollModule module, Object[] obj) {
		super.handleCreationArgs(module, obj);
		if (obj[0] instanceof TiViewProxy) {
			addTiViewToWindowManager((TiViewProxy) obj[0]);

		} else
			Log.e(LCAT, "Paramzer must be a viewproxy!");
	}

	private void addTiViewToWindowManager(TiViewProxy proxy) {
		// extracting TiUIView from proxy:
		TiUIView contentView = proxy.getOrCreateView();
		// extracting native view from TiUIView:
		View outerView = contentView.getOuterView();
		nativeView = outerView != null ? outerView : contentView.getNativeView();
		Log.d(LCAT, "nativeView height=" + nativeView.getHeight()); // 0 !
		windowManager.addView(nativeView, params);

		nativeView.setOnTouchListener(new View.OnTouchListener() {
			private int initialX;
			private int initialY;
			private float initialTouchX;
			private float initialTouchY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					initialX = params.x;
					initialY = params.y;
					initialTouchX = event.getRawX();
					initialTouchY = event.getRawY();
					return true;

				case MotionEvent.ACTION_MOVE:
					// this code is helping the widget to move around the screen with fingers
					params.x = initialX + (int) (event.getRawX() - initialTouchX);
					params.y = initialY + (int) (event.getRawY() - initialTouchY);
					// windowManager.updateViewLayout(view, params);
					return true;
				}
				return false;
			}
		});

	}
}
