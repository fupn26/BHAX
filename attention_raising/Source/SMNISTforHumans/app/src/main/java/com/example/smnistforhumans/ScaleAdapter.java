package com.example.smnistforhumans;

public class ScaleAdapter extends android.view.ScaleGestureDetector.SimpleOnScaleGestureListener {

    private SMNISTSurfaceView surfaceView;

    public ScaleAdapter(SMNISTSurfaceView surfaceView) {
        this.surfaceView = surfaceView;
    }

    @Override
    public boolean onScale(android.view.ScaleGestureDetector detector) {

        float scaleFactor = surfaceView.getScaleFactor() * detector.getScaleFactor();
        surfaceView.setScaleFactor(Math.max(1.0f, Math.min(scaleFactor, 3.0f)));

        surfaceView.repaint();

        return true;
    }

}
