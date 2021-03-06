package com.girlassistant.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import com.girlassistant.base.App;
import com.girlassistant.base.BgThread;
import com.girlassistant.base.FileCache;
import com.girlassistant.base.ImageCache;
import com.girlassistant.imageloader.ImageLoader;
import com.girlassistant.activity.R;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

public class ImageUtils {

	public static boolean isImage(String url) {
		if (url.startsWith("content")) {
			return true;
		}
		String extention = url.substring(url.lastIndexOf(".") + 1);
		String[] extentions = { "jpg", "jpeg", "png", "ico", "bmp", "gif" };
		for (int i = 0; i < extentions.length; i++) {
			if (extention.equalsIgnoreCase(extentions[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 常规imageview 加载网络图片，需要加载图片的情况下
	 * 
	 * @param imageUrl
	 *            图片地址
	 * @param imageView
	 *            图片控件
	 * @param resId
	 *            默认资源id
	 */
	public static void setImageViewBitmap(String imageUrl, ImageView imageView, int resId) {
		if (imageView == null) {
			return;
		}
		if (imageUrl == null || "".equals(imageUrl)) {
			if (resId != 0) {
				imageView.setImageBitmap(readBitmapFromResource(resId));
			}
			return;
		}
		Bitmap bitmap = ImageCache.getInstance().getBitmapFromMemory(imageUrl);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
			imageView.setVisibility(View.VISIBLE);
		} else {
			if (resId != 0) {
				imageView.setImageBitmap(readBitmapFromResource(resId));
			}
			ImageLoader.start(imageUrl, imageView);
		}
	}

	public static void setImageViewBitmapWithCrop(String imageUrl, ImageView imageView, int resId, int cropWidth, int cropHeight) {
		if (imageView == null) {
			return;
		}
		if (imageUrl == null || "".equals(imageUrl)) {
			if (resId != 0) {
				imageView.setImageBitmap(readBitmapFromResource(resId));
			}
			return;
		}
		Bitmap bitmap = ImageCache.getInstance().getBitmapFromMemory(imageUrl);
		if (bitmap != null) {
			bitmap = corp(bitmap, cropWidth, cropHeight);
			imageView.setImageBitmap(bitmap);
			imageView.setVisibility(View.VISIBLE);
		} else {
			if (resId != 0) {
				imageView.setImageBitmap(readBitmapFromResource(resId));
			}
			ImageLoader.start(imageUrl, imageView);
		}
	}

	/**
	 * 常规imageview 加载网络图片，需要加载图片的情况下
	 * 
	 * @param imageUrl
	 *            图片地址
	 * @param imageView
	 *            图片控件
	 */
	public static void setImageViewBitmap(String imageUrl, ImageView imageView) {
		if (imageView == null || imageUrl == null || "".equals(imageUrl)) {
			return;
		}
		Bitmap bitmap = ImageCache.getInstance().getBitmapFromMemory(imageUrl);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		} else {
			ImageLoader.start(imageUrl, imageView);
		}
	}

	public static void setImageBackgroundViewBitmap(String imageUrl, ImageView imageView) {
		if (imageView == null || imageUrl == null || "".equals(imageUrl)) {
			return;
		}
		Bitmap bitmap = ImageCache.getInstance().getBitmapFromMemory(imageUrl);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		} else {
			ImageLoader.start(imageUrl, imageView);
		}
	}

	public static void setImageViewBitmap(String imageUrl, ImageView imageView, int width, int height, int resId) {
		if (imageView == null) {
			return;
		}
		if (imageUrl == null || "".equals(imageUrl)) {
			imageView.setImageBitmap(readBitmapFromResource(resId));
			return;
		}
		Bitmap bitmap = ImageCache.getInstance().getBitmapFromMemory(imageUrl);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
			imageView.setVisibility(View.VISIBLE);
		} else {
			imageView.setImageBitmap(readBitmapFromResource(resId));
			ImageLoader.start(imageUrl, imageView, width, height);
		}
	}

	public static void setImageViewBitmap(String imageUrl, ImageView imageView, int reqWidth, int reqHeight, Drawable background) {
		if (imageView == null) {
			return;
		}
		if (imageUrl == null || "".equals(imageUrl)) {
			CompatUtils.setViewBackground(imageView, background);
			return;
		}
		Bitmap bitmap = ImageCache.getInstance().getBitmapFromMemory(imageUrl);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
			imageView.setVisibility(View.VISIBLE);
		} else {
			CompatUtils.setViewBackground(imageView, background);
			ImageLoader.start(imageUrl, imageView, reqWidth, reqHeight);
		}
	}

	/**
	 * 压缩imageview 加载网络图片，需要加载图片的情况下
	 * 
	 * @param imageUrl
	 *            图片地址
	 * @param imageView
	 *            图片控件
	 * @param resId
	 *            默认背景图片
	 * @param width
	 *            压缩宽度
	 * @param height
	 *            压缩高度
	 */
	public static void setImageViewBitmapDefaultBackground(String imageUrl, ImageView imageView, int width, int height, int resId) {
		if (imageView == null) {
			return;
		}
		imageView.setBackgroundResource(resId);
		if (imageUrl == null || "".equals(imageUrl)) {
			return;
		}
		Bitmap bitmap = ImageCache.getInstance().getBitmapFromMemory(imageUrl);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		} else {
			ImageLoader.start(imageUrl, imageView, width, height);
		}
	}

	public static void getImageViewBitmap(String imageUrl, int resId, ImageCallback callback) {
		if (imageUrl == null || "".equals(imageUrl)) {
			callback.imageLoaded(readBitmapFromResource(resId), 0, 0);
			return;
		}
		Bitmap bitmap = ImageCache.getInstance().getBitmapFromMemory(imageUrl);
		if (bitmap != null) {
			callback.imageLoaded(bitmap, 0, 0);
		} else {
			ImageLoader.start(imageUrl, callback);
		}
	}

	public static void getImageViewBitmapWidthCrop(String imageUrl, int cropWidth, int cropHeight, int resId, ImageCallback callback) {
		if (imageUrl == null || "".equals(imageUrl)) {
			callback.imageLoaded(readBitmapFromResource(resId), 0, 0);
			return;
		}
		Bitmap bitmap = ImageCache.getInstance().getBitmapFromMemory(imageUrl);
		if (bitmap != null) {
			bitmap = corp(bitmap, cropWidth, cropHeight);
			callback.imageLoaded(bitmap, 0, 0);
		} else {
			ImageLoader.start(imageUrl, callback);
		}
	}

	public static int getThumbnailRate(int originHeight, int originWidth, int height, int width) {
		float heightRate = originHeight / (float) height;
		float widthRate = originWidth / (float) width;

		return (int) Math.min(heightRate, widthRate);
	}

	/**
	 * 计算缩略图尺寸比例
	 * 
	 * @param options
	 *            请求尺寸大小
	 * @param reqWidth
	 *            实际的大小
	 * @param reqHeight
	 *            实际的大小
	 * @return
	 */
	private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
			// This offers some additional logic in case the image has a strange
			// aspect ratio. For example, a panorama may have a much larger
			// width than height. In these cases the total pixels might still
			// end up being too large to fit comfortably in memory, so we should
			// be more aggressive with sample down the image (=larger
			// inSampleSize).

			final float totalPixels = width * height;

			// Anything more than 2x the requested pixels we'll sample down
			// further.
			final float totalReqPixelsCap = reqWidth * reqHeight * 2;
			if (inSampleSize <= 0) {
				inSampleSize = 1;
			}

			while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
				inSampleSize++;
			}
		}
		return inSampleSize;
	}

	/**
	 * 用于获取对应id图片的bitmap
	 * 
	 * @param res
	 * @param resId
	 * @return
	 */
	public static Bitmap decodeBitmapFromResource(Resources res, int resId) {
		return BitmapFactory.decodeResource(res, resId);
	}

	public static Bitmap decodeSampledBitmapFromUri(Uri uri, int reqWidth, int reqHeight, Context context) throws FileNotFoundException {
		ContentResolver cr = context.getContentResolver();
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(cr.openInputStream(uri), null, options);
		if (options.mCancel || options.outWidth == -1 || options.outHeight == -1) {
			return null;
		}
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeStream(cr.openInputStream(uri), null, options);
	}

	public static int getImageMaxSizeByMemory() {
		int size = 150;// 单位为kb

		int memorySize = App.getInstance().getSystemMaxMemory();
		if (memorySize <= 16) {
			size = 150;
		} else if (memorySize <= 32) {
			size = 300;
		} else if (memorySize <= 48) {
			size = 500;
		} else if (memorySize <= 96) {
			size = 600;
		} else {
			size = 720;
		}

		return size * 1024;
	}

	public static Bitmap decodeSampledBitmapFromFile(String filePath, int reqWidth, int reqHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Config.RGB_565;
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		if (options.mCancel || options.outWidth == -1 || options.outHeight == -1) {
			return null;
		}
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		File file = new File(filePath);
		long size = file.length();
		long tempSize = file.length() / options.inSampleSize;
		int maxImageSize = getImageMaxSizeByMemory();
		while (tempSize > maxImageSize) {
			options.inSampleSize++;
			tempSize = size / options.inSampleSize;
		}
		file.setLastModified(System.currentTimeMillis());

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		try {
			return BitmapFactory.decodeFile(filePath, options);
		} catch (OutOfMemoryError e) {
			return null;
		}
	}

	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);
		if (options.mCancel || options.outWidth == -1 || options.outHeight == -1) {
			return null;
		}
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	public static Bitmap decodeSampledBitmapFromByteArray(byte[] data, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(data, 0, data.length, options);
		if (options.mCancel || options.outWidth == -1 || options.outHeight == -1) {
			return null;
		}
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeByteArray(data, 0, data.length, options);
	}

	/**
	 * Get the size in bytes of a bitmap.
	 * 
	 * @param bitmap
	 * @return size in bytes
	 */
	@SuppressLint("NewApi")
	public static int getBitmapSize(Bitmap bitmap) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
			return bitmap.getByteCount();
		}
		// Pre HC-MR1
		return bitmap.getRowBytes() * bitmap.getHeight();
	}

	public static Bitmap readImageFromFile(File file) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Config.RGB_565;
		options.inSampleSize = 1;
		long size = file.length();
		if (size == 0) {
			file.delete();
			return null;
		}

		long tempSize = size / options.inSampleSize;
		int maxImageSize = getImageMaxSizeByMemory() * 3;// 最大尺寸的3倍
		while ((tempSize - maxImageSize) > 50000) {// 50kb的空间，最可能为了清晰
			options.inSampleSize++;
			tempSize = size / options.inSampleSize;
		}
		file.setLastModified(System.currentTimeMillis());
		try {
			return BitmapFactory.decodeFile(file.getPath(), options);
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			ImageCache.getInstance().clearMemory();
			try {
				options.inSampleSize += 2;// 返回一个压缩的图片四分之一大小
				return BitmapFactory.decodeFile(file.getPath(), options);
			} catch (OutOfMemoryError ee) {
				ee.printStackTrace();
			}
		}
		return null;
	}

	public static Bitmap readBitmapFromResource(int resId) {
		if (resId == 0) {
			return null;
		}
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Config.RGB_565;
		opt.inDither = false;
		opt.inPurgeable = true; // Tell to gc that whether it needs free memory,
								// the Bitmap can be cleared 当内存不足时优先释放的部分
		opt.inInputShareable = true; // Which kind of reference will be used to
										// recover the Bitmap data after being
										// clear, when it will be used in the
										// future
		// 获取资源图片
		InputStream is = App.getInstance().getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	public static Bitmap readBitmapFromResource(int resId, int reqWidth, int reqHeight) {
		InputStream is = App.getInstance().getResources().openRawResource(resId);
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(is, null, options);
		if (options.mCancel || options.outWidth == -1 || options.outHeight == -1) {
			return null;
		}
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inDither = false;
		options.inPurgeable = true; // Tell to gc that whether it needs free
									// memory, the Bitmap can be cleared
		options.inInputShareable = true; // Which kind of reference will be used
											// to recover the Bitmap data after
											// being clear, when it will be used
											// in the future
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeStream(is, null, options);
	}

	// public static void loadBitmapOnlyWifi(String path, ImageView imageView,
	// boolean isWifi) {
	//
	// if (isWifi) {
	//
	// if (CommonUtils.isWifiConnected()) {
	// ImageUtils.setImageViewBitmap(path, imageView, R.drawable.pic_default);
	// } else {
	//
	// imageView.setImageBitmap(readBitmapFromResource(R.drawable.pic_default));
	// }
	//
	// } else {
	//
	// ImageUtils.setImageViewBitmap(path, imageView, R.drawable.pic_default);
	// }
	// }

	public static void loadBitmapOnlyWifi2(String path, ImageView imageView, boolean isWifi) {

		if (isWifi) {

			if (CommonUtils.isWifiConnected()) {
				ImageUtils.setImageViewBitmap(path, imageView, 0);
			} else {
				// imageView.setImageBitmap(readBitmapFromResource(R.drawable.pic_default));
			}

		} else {

			ImageUtils.setImageViewBitmap(path, imageView, 0);
		}
	}

	public static void loadBitmapOnlyWifi(String path, ImageView imageView, boolean isWifi, int defaultDrawable) {
		// //测试展位图
		// {
		// path+="11";
		// }
		if (isWifi) {

			if (CommonUtils.isWifiConnected()) {
				ImageUtils.setImageViewBitmap(path, imageView, defaultDrawable);
			} else {

				imageView.setImageBitmap(readBitmapFromResource(defaultDrawable));
			}

		} else {

			ImageUtils.setImageViewBitmap(path, imageView, defaultDrawable);
		}
	}

	public static void loadBitmapOnlyWifiWithCrop(String path, ImageView imageView, boolean isWifi, int defaultDrawable, int cropWidth, int cropHeight) {
		// //测试展位图
		// {
		// path+="11";
		// }
		if (isWifi) {

			if (CommonUtils.isWifiConnected()) {
				ImageUtils.setImageViewBitmapWithCrop(path, imageView, defaultDrawable, cropWidth, cropHeight);
			} else {

				imageView.setImageBitmap(readBitmapFromResource(defaultDrawable));
			}

		} else {

			ImageUtils.setImageViewBitmapWithCrop(path, imageView, defaultDrawable, cropWidth, cropHeight);
		}
	}

	// public static void loadBitmapOnlyWifi(String path, TextView textView,
	// boolean isWifi) {
	//
	// if (isWifi) {
	//
	// if (CommonUtils.isWifiConnected()) {
	// ImageUtils.setImageViewBitmap(path, textView, R.drawable.pic_default);
	// } else {
	//
	// textView.setImageBitmap(readBitmapFromResource(R.drawable.pic_default));
	// }
	//
	// } else {
	//
	// ImageUtils.setImageViewBitmap(path, textView, R.drawable.pic_default);
	// }
	// }

	public static void loadCallBackBitmapOnlyWifi(String path, boolean isWifi, ImageCallback callback, int resouce) {

		if (isWifi) {

			if (CommonUtils.isWifiConnected()) {
				getImageViewBitmap(path, resouce, callback);
			} else {

				callback.imageLoaded(readBitmapFromResource(R.drawable.pic_default), 0, 0);
			}

		} else {

			getImageViewBitmap(path, resouce, callback);
		}
	}

	/**
	 * 多图图片宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getMulImageWidth(Context context) {
		int width = DeviceParameter.getIntScreenWidth();
		int imagWidth = (width - DeviceParameter.dip2px(context, 24) - 52) / 3;
		return imagWidth;
	}

	/**
	 * 多图图片高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getMulImagHeight(Context context) {
		int imagWidth = getMulImageWidth(context);
		int imagHeight = imagWidth * 3 / 4;
		MLog.d("图片" + "(多图) 宽：" + imagWidth + "   高：" + imagHeight);
		return imagHeight;
	}

	public static int getBothImageWidth() {
		int width = DeviceParameter.getIntScreenWidth();
		int imagWidth = width / 2;
		return imagWidth;
	}

	public static int getBothImageHeight() {
		int imageWidth = getBothImageWidth();
		int imageHeight = imageWidth * 3 / 4;
		return imageHeight;
	}

	/**
	 * 专题图片宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getSingImageWidth(Context context) {
		int width = DeviceParameter.getIntScreenWidth();
		int imagWidth = width - DeviceParameter.dip2px(context, 24);
		return imagWidth;
	}

	/**
	 * 专题图片高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getSingImageHeight(Context context) {
		int imagWidth = getSingImageWidth(context);
		int imagHeight = imagWidth * 6 / 17;
		MLog.d("图片" + "(专题) 宽：" + imagWidth + "   高：" + imagHeight);
		return imagHeight;
	}

	public static int getPicImageHeight(Context context) {
		int imageWidth = getSingImageWidth(context);
		int imageHeight = imageWidth * 13 / 30;
		return imageHeight;
	}

	/**
	 * 头图图片宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getTopImageWidth(Context context) {
		int width = DeviceParameter.getIntScreenWidth();
		int imageWidth = width;
		return imageWidth;
	}

	/**
	 * 头图高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getTopImageHeight(Context context) {
		int imageWidth = getTopImageWidth(context);
		int imageHeight = imageWidth / 2;
		MLog.d("图片" + "(头图) 宽：" + imageWidth + "   高：" + imageHeight);
		return imageHeight;
	}

	// 对外界开放的回调接口
	public static interface ImageCallback {
		// 注意 此方法是用来设置目标对象的图像资源
		public void imageLoaded(Bitmap imageDrawable, int width, int height);
	}

	public static void downLoadImageUrl(String imageUrl, String newsId, int index, int flag) {
		Bitmap bitmap = ImageCache.getInstance().getBitmapFromMemory(imageUrl);
		if (bitmap != null) {
			return;
		} else {
			ImageLoader.start(imageUrl);
		}
	}

	public static Bitmap corp(Bitmap bitmap, int cropWidth, int cropHeight) {

		int corpWith = cropWidth;
		int corpHeight = cropHeight;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int srcLeft = 0;
		int srcTop = 0;
		int dstLeft = 0;
		int dstTop = 0;

		Bitmap output = Bitmap.createBitmap(corpWith, corpHeight, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		if (corpWith >= width) {
			dstLeft = (corpWith - width) / 2;
			corpWith = width;
		} else {
			srcLeft = (width - corpWith) / 2;
		}
		if (corpHeight >= height) {
			dstTop = (corpHeight - height) / 2;
			corpHeight = height;
		} else {
			srcTop = (height - corpHeight) / 2;
		}

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect dstRect = new Rect(dstLeft, dstTop, corpWith + dstLeft, corpHeight + dstTop);
		final Rect srcRect = new Rect(srcLeft, srcTop, corpWith + srcLeft, corpHeight + srcTop);
		final RectF rectF = new RectF(dstRect);
		final float roundPx = 5; // 圆角像素

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, srcRect, dstRect, paint);

		return output;
	}

	public static void copyImageToSdcard(final Context context) {

		new BgThread() {

			@Override
			public void doTask() {

				InputStream is = null;
				FileOutputStream fos = null;
				byte[] buffer = new byte[1024];
				int count = 0;

				try {
					is = context.getAssets().open("ic_launcher.png");
					if (FileCache.getDefaultLogoPath() != null) {
						File f = new File(FileCache.getDefaultLogoPath() + File.separator + FileCache.DEFAULT_LOGO_NAME);
						if (f.exists()) {
							return;
						}
						f.createNewFile();
						fos = new FileOutputStream(f);
						while ((count = is.read(buffer)) > 0) {
							fos.write(buffer, 0, count);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (is != null) {
							is.close();
						}

						if (fos != null) {
							fos.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();

	}

}
