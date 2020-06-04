package com.kook.tools.util;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.lang.reflect.Method;
public class EmulatorDetector {
    private static final String TAG = "EmulatorDetector";
    private static int rating = -1;
    public static boolean isEmulatorAbsoluly(Context context) {
        if (mayOnEmulatorViaQEMU(context)) {
            return true;
        }
        if (Build.PRODUCT.contains("sdk") ||
            Build.PRODUCT.contains("sdk_x86") ||
            Build.PRODUCT.contains("sdk_google") ||
            Build.PRODUCT.contains("Andy") ||
            Build.PRODUCT.contains("Droid4X") ||
            Build.PRODUCT.contains("nox") ||
            Build.PRODUCT.contains("vbox86p") ||
            Build.PRODUCT.contains("aries")) {
            return true;
        }
        if (Build.MANUFACTURER.equals("Genymotion") ||
            Build.MANUFACTURER.contains("Andy") ||
            Build.MANUFACTURER.contains("nox") ||
            Build.MANUFACTURER.contains("TiantianVM")) {
            return true;
        }
        if (Build.BRAND.contains("Andy")) {
            return true;
        }
        if (Build.DEVICE.contains("Andy") ||
            Build.DEVICE.contains("Droid4X") ||
            Build.DEVICE.contains("nox") ||
            Build.DEVICE.contains("vbox86p") ||
            Build.DEVICE.contains("aries")) {
            return true;
        }
        if (Build.MODEL.contains("Emulator") ||
            Build.MODEL.equals("google_sdk") ||
            Build.MODEL.contains("Droid4X") ||
            Build.MODEL.contains("TiantianVM") ||
            Build.MODEL.contains("Andy") ||
            Build.MODEL.equals("Android SDK built for x86_64") ||
            Build.MODEL.equals("Android SDK built for x86")) {
            return true;
        }
        if (Build.HARDWARE.equals("vbox86") ||
            Build.HARDWARE.contains("nox") ||
            Build.HARDWARE.contains("ttVM_x86")) {
            return true;
        }
        if (Build.FINGERPRINT.contains("generic/sdk/generic") ||
            Build.FINGERPRINT.contains("generic_x86/sdk_x86/generic_x86") ||
            Build.FINGERPRINT.contains("Andy") ||
            Build.FINGERPRINT.contains("ttVM_Hdragon") ||
            Build.FINGERPRINT.contains("generic/google_sdk/generic") ||
            Build.FINGERPRINT.contains("vbox86p") ||
            Build.FINGERPRINT.contains("generic/vbox86p/vbox86p")) {
            return true;
        }
        return false;
    }
    public static boolean isEmulator(Context context) {
        if (isEmulatorAbsoluly(context)) {
            return true;
        }
        int newRating = 0;
        if (rating < 0) {
            if (Build.PRODUCT.contains("sdk") ||
                Build.PRODUCT.contains("Andy") ||
                Build.PRODUCT.contains("ttVM_Hdragon") ||
                Build.PRODUCT.contains("google_sdk") ||
                Build.PRODUCT.contains("Droid4X") ||
                Build.PRODUCT.contains("nox") ||
                Build.PRODUCT.contains("sdk_x86") ||
                Build.PRODUCT.contains("sdk_google") ||
                Build.PRODUCT.contains("vbox86p")||
                Build.PRODUCT.contains("aries")) {
                newRating++;
            }
            if (Build.MANUFACTURER.equals("unknown") ||
                Build.MANUFACTURER.equals("Genymotion") ||
                Build.MANUFACTURER.contains("Andy") ||
                Build.MANUFACTURER.contains("MIT") ||
                Build.MANUFACTURER.contains("nox") ||
                Build.MANUFACTURER.contains("TiantianVM")) {
                newRating++;
            }
            if (Build.BRAND.equals("generic") ||
                Build.BRAND.equals("generic_x86") ||
                Build.BRAND.equals("TTVM") ||
                Build.BRAND.contains("Andy")) {
                newRating++;
            }
            if (Build.DEVICE.contains("generic") ||
                Build.DEVICE.contains("generic_x86") ||
                Build.DEVICE.contains("Andy") ||
                Build.DEVICE.contains("ttVM_Hdragon") ||
                Build.DEVICE.contains("Droid4X") ||
                Build.DEVICE.contains("nox") ||
                Build.DEVICE.contains("generic_x86_64") ||
                Build.DEVICE.contains("vbox86p")||
                Build.DEVICE.contains("aries")) {
                newRating++;
            }
            if (Build.MODEL.equals("sdk") ||
                Build.MODEL.contains("Emulator") ||
                Build.MODEL.equals("google_sdk") ||
                Build.MODEL.contains("Droid4X") ||
                Build.MODEL.contains("TiantianVM") ||
                Build.MODEL.contains("Andy") ||
                Build.MODEL.equals("Android SDK built for x86_64") ||
                Build.MODEL.equals("Android SDK built for x86")) {
                newRating++;
            }
            if (Build.HARDWARE.equals("goldfish") ||
                Build.HARDWARE.equals("vbox86") ||
                Build.HARDWARE.contains("nox") ||
                Build.HARDWARE.contains("ttVM_x86")) {
                newRating++;
            }
            if (Build.FINGERPRINT.contains("generic/sdk/generic") ||
                Build.FINGERPRINT.contains("generic_x86/sdk_x86/generic_x86") ||
                Build.FINGERPRINT.contains("Andy") ||
                Build.FINGERPRINT.contains("ttVM_Hdragon") ||
                Build.FINGERPRINT.contains("generic_x86_64") ||
                Build.FINGERPRINT.contains("generic/google_sdk/generic") ||
                Build.FINGERPRINT.contains("vbox86p") ||
                Build.FINGERPRINT.contains("generic/vbox86p/vbox86p")) {
                newRating++;
            }
            try {
                String opengl = android.opengl.GLES20.glGetString(android.opengl.GLES20.GL_RENDERER);
                if (opengl != null) {
                    if (opengl.contains("Bluestacks") ||
                        opengl.contains("Translator")
                        ) {
                        newRating += 10;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                DebugKook.printException(e);
            }
            try {
                File sharedFolder = new File(Environment
                    .getExternalStorageDirectory().toString()
                    + File.separatorChar
                    + "windows"
                    + File.separatorChar
                    + "BstSharedFolder");
                if (sharedFolder.exists()) {
                    newRating += 10;
                }
            } catch (Exception e) {
                e.printStackTrace();
                DebugKook.printException(e);
            }
            rating = newRating;
        }
        return rating > 3;
    }
    private static final boolean mayOnEmulatorViaQEMU(Context context) {
        String qemu = getProp(context, "ro.kernel.qemu");
        return "1".equals(qemu);
    }
    public static String getDeviceListing() {
        return "Build.PRODUCT: " + Build.PRODUCT + "\n" +
            "Build.MANUFACTURER: " + Build.MANUFACTURER + "\n" +
            "Build.BRAND: " + Build.BRAND + "\n" +
            "Build.DEVICE: " + Build.DEVICE + "\n" +
            "Build.MODEL: " + Build.MODEL + "\n" +
            "Build.HARDWARE: " + Build.HARDWARE + "\n" +
            "Build.FINGERPRINT: " + Build.FINGERPRINT + "\n" +
            "Build.TAGS: " + Build.TAGS + "\n" +
            "GL_RENDERER: " + android.opengl.GLES20.glGetString(android.opengl.GLES20.GL_RENDERER) + "\n" +
            "GL_VENDOR: " + android.opengl.GLES20.glGetString(android.opengl.GLES20.GL_VENDOR) + "\n" +
            "GL_VERSION: " + android.opengl.GLES20.glGetString(android.opengl.GLES20.GL_VERSION) + "\n" +
            "GL_EXTENSIONS: " + android.opengl.GLES20.glGetString(android.opengl.GLES20.GL_EXTENSIONS) + "\n";
    }
    private static final String getProp(Context context, String property) {
        try {
            ClassLoader cl = context.getClassLoader();
            Class<?> SystemProperties = cl.loadClass("android.os.SystemProperties");
            Method method = SystemProperties.getMethod("get", String.class);
            Object[] params = new Object[1];
            params[0] = property;
            return (String) method.invoke(SystemProperties, params);
        } catch (Exception e) {
            DebugKook.printException(e);
            return null;
        }
    }
    public static void logcat() {
        Log.d(TAG, getDeviceListing());
    }
}
