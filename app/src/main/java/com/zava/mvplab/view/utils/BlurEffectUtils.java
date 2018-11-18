
package com.zava.mvplab.view.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;

import com.squareup.picasso.Transformation;

public class BlurEffectUtils implements Transformation {

  private static final int UP_LIMIT = 25;
  private static final int LOW_LIMIT = 1;
  protected final Context context;
  private final int blurRadius;

  public BlurEffectUtils(Context context, int radius) {
    this.context = context;

    if (radius < LOW_LIMIT) {
      this.blurRadius = LOW_LIMIT;
    } else if (radius > UP_LIMIT) {
      this.blurRadius = UP_LIMIT;
    } else {
      this.blurRadius = radius;
    }
  }

  @Override public Bitmap transform(Bitmap source) {

    Bitmap blurredBitmap;
    blurredBitmap = Bitmap.createBitmap(source);

    RenderScript renderScript = RenderScript.create(context);

    Allocation input =
        Allocation.createFromBitmap(renderScript, source, Allocation.MipmapControl.MIPMAP_FULL,
            Allocation.USAGE_SCRIPT);

    Allocation output = Allocation.createTyped(renderScript, input.getType());

    ScriptIntrinsicBlur script =
        ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));

    script.setInput(input);
    script.setRadius(blurRadius);

    script.forEach(output);
    output.copyTo(blurredBitmap);

    return blurredBitmap;
  }

  @Override public String key() {
    return "blurred";
  }
}