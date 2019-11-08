package com.example.theham.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.theham.R;

public class ScanFragment extends Fragment {

    //   ImageView image;
//    Button scan_btn;
//
//    QuadrilateralSelectionImageView mSelectionImageView;
//
//
//    MainActivity mainActivity = (MainActivity) getActivity();
//
//    Bitmap mBitmap;
//    Bitmap mResult;
//
//    MaterialDialog mResultDialog;
//    OpenCVCallback mOpenCVLoaderCallback;
//
//    private static final int MAX_HEIGHT = 500;
//
//    private int PICK_IMAGE_REQUEST = 1;


    public ScanFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_scan, container, false);
        return v;

    }


//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//
//        mainActivity.getMenuInflater().inflate(R.menu.activity_main_menu, menu);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        initOpenCV();
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_gallery) {
//            Intent intent = new Intent();
//            intent.setType("image/*");
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
//        } else if (id == R.id.action_camera) {
//            // TODO Camera
//
//            new MaterialDialog.Builder(mainActivity)
//                    .title("TODO")
//                    .content("The camera is a TODO item.")
//                    .positiveText("OK")
//                    .show();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//
//            Uri uri = data.getData();
//
//            try {
//                mBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
//                mSelectionImageView.setImageBitmap(getResizedBitmap(mBitmap, MAX_HEIGHT));
//                List<PointF> points = findPoints();
//                mSelectionImageView.setPoints(points);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void initOpenCV() {
//        if (!OpenCVLoader.initDebug()) {
//            Timber.d("Internal OpenCV library not found. Using OpenCV Manager for initialization");
//            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_1_0, mainActivity, mOpenCVLoaderCallback);
//        } else {
//            Timber.d("OpenCV library found inside package. Using it!");
//            mOpenCVLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
//        }
//    }
//
//    private Bitmap getResizedBitmap(Bitmap bitmap, int maxHeight) {
//        double ratio = bitmap.getHeight() / (double) maxHeight;
//        int width = (int) (bitmap.getWidth() / ratio);
//        return Bitmap.createScaledBitmap(bitmap, width, maxHeight, false);
//    }
//
//
//    private List<PointF> findPoints() {
//        List<PointF> result = null;
//
//        Mat image = new Mat();
//        Mat orig = new Mat();
//        org.opencv.android.Utils.bitmapToMat(getResizedBitmap(mBitmap, MAX_HEIGHT), image);
//        org.opencv.android.Utils.bitmapToMat(mBitmap, orig);
//
//        Mat edges = edgeDetection(image);
//        MatOfPoint2f largest = findLargestContour(edges);
//
//        if (largest != null) {
//            Point[] points = sortPoints(largest.toArray());
//            result = new ArrayList<>();
//            result.add(new PointF(Double.valueOf(points[0].x).floatValue(), Double.valueOf(points[0].y).floatValue()));
//            result.add(new PointF(Double.valueOf(points[1].x).floatValue(), Double.valueOf(points[1].y).floatValue()));
//            result.add(new PointF(Double.valueOf(points[2].x).floatValue(), Double.valueOf(points[2].y).floatValue()));
//            result.add(new PointF(Double.valueOf(points[3].x).floatValue(), Double.valueOf(points[3].y).floatValue()));
//            largest.release();
//        } else {
//            Timber.d("Can't find rectangle!");
//        }
//
//        edges.release();
//        image.release();
//        orig.release();
//
//        return result;
//    }
//
//    private Mat edgeDetection(Mat src) {
//        Mat edges = new Mat();
//        Imgproc.cvtColor(src, edges, Imgproc.COLOR_BGR2GRAY);
//        Imgproc.GaussianBlur(edges, edges, new Size(5, 5), 0);
//        Imgproc.Canny(edges, edges, 75, 200);
//        return edges;
//    }
//
//    private MatOfPoint2f findLargestContour(Mat src) {
//        List<MatOfPoint> contours = new ArrayList<>();
//        Imgproc.findContours(src, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
//
//        // Get the 5 largest contours
//        Collections.sort(contours, new Comparator<MatOfPoint>() {
//            public int compare(MatOfPoint o1, MatOfPoint o2) {
//                double area1 = Imgproc.contourArea(o1);
//                double area2 = Imgproc.contourArea(o2);
//                return (int) (area2 - area1);
//            }
//        });
//        if (contours.size() > 5) contours.subList(4, contours.size() - 1).clear();
//
//        MatOfPoint2f largest = null;
//        for (MatOfPoint contour : contours) {
//            MatOfPoint2f approx = new MatOfPoint2f();
//            MatOfPoint2f c = new MatOfPoint2f();
//            contour.convertTo(c, CvType.CV_32FC2);
//            Imgproc.approxPolyDP(c, approx, Imgproc.arcLength(c, true) * 0.02, true);
//
//            if (approx.total() == 4 && Imgproc.contourArea(contour) > 150) {
//                // the contour has 4 points, it's valid
//                largest = approx;
//                break;
//            }
//        }
//
//        return largest;
//    }
//
//    private Mat perspectiveTransform(Mat src, List<PointF> points) {
//        Point point1 = new Point(points.get(0).x, points.get(0).y);
//        Point point2 = new Point(points.get(1).x, points.get(1).y);
//        Point point3 = new Point(points.get(2).x, points.get(2).y);
//        Point point4 = new Point(points.get(3).x, points.get(3).y);
//        Point[] pts = {point1, point2, point3, point4};
//        return fourPointTransform(src, sortPoints(pts));
//    }
//
//    private Bitmap applyThreshold(Mat src) {
//        Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2GRAY);
//
//        // Some other approaches
////        Imgproc.adaptiveThreshold(src, src, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 15, 15);
////        Imgproc.threshold(src, src, 0, 255, Imgproc.THRESH_BINARY + Imgproc.THRESH_OTSU);
//
//        Imgproc.GaussianBlur(src, src, new Size(5, 5), 0);
//        Imgproc.adaptiveThreshold(src, src, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 11, 2);
//
//        Bitmap bm = Bitmap.createBitmap(src.width(), src.height(), Bitmap.Config.ARGB_8888);
//        org.opencv.android.Utils.matToBitmap(src, bm);
//
//        return bm;
//    }
//
//    private Point[] sortPoints(Point[] src) {
//        ArrayList<Point> srcPoints = new ArrayList<>(Arrays.asList(src));
//        Point[] result = {null, null, null, null};
//
//        Comparator<Point> sumComparator = new Comparator<Point>() {
//            @Override
//            public int compare(Point lhs, Point rhs) {
//                return Double.valueOf(lhs.y + lhs.x).compareTo(rhs.y + rhs.x);
//            }
//        };
//        Comparator<Point> differenceComparator = new Comparator<Point>() {
//            @Override
//            public int compare(Point lhs, Point rhs) {
//                return Double.valueOf(lhs.y - lhs.x).compareTo(rhs.y - rhs.x);
//            }
//        };
//
//        result[0] = Collections.min(srcPoints, sumComparator);        // Upper left has the minimal sum
//        result[2] = Collections.max(srcPoints, sumComparator);        // Lower right has the maximal sum
//        result[1] = Collections.min(srcPoints, differenceComparator); // Upper right has the minimal difference
//        result[3] = Collections.max(srcPoints, differenceComparator); // Lower left has the maximal difference
//
//        return result;
//    }
//
//    private Mat fourPointTransform(Mat src, Point[] pts) {
//        double ratio = src.size().height / (double) MAX_HEIGHT;
//
//        Point ul = pts[0];
//        Point ur = pts[1];
//        Point lr = pts[2];
//        Point ll = pts[3];
//
//        double widthA = Math.sqrt(Math.pow(lr.x - ll.x, 2) + Math.pow(lr.y - ll.y, 2));
//        double widthB = Math.sqrt(Math.pow(ur.x - ul.x, 2) + Math.pow(ur.y - ul.y, 2));
//        double maxWidth = Math.max(widthA, widthB) * ratio;
//
//        double heightA = Math.sqrt(Math.pow(ur.x - lr.x, 2) + Math.pow(ur.y - lr.y, 2));
//        double heightB = Math.sqrt(Math.pow(ul.x - ll.x, 2) + Math.pow(ul.y - ll.y, 2));
//        double maxHeight = Math.max(heightA, heightB) * ratio;
//
//        Mat resultMat = new Mat(Double.valueOf(maxHeight).intValue(), Double.valueOf(maxWidth).intValue(), CvType.CV_8UC4);
//
//        Mat srcMat = new Mat(4, 1, CvType.CV_32FC2);
//        Mat dstMat = new Mat(4, 1, CvType.CV_32FC2);
//        srcMat.put(0, 0, ul.x * ratio, ul.y * ratio, ur.x * ratio, ur.y * ratio, lr.x * ratio, lr.y * ratio, ll.x * ratio, ll.y * ratio);
//        dstMat.put(0, 0, 0.0, 0.0, maxWidth, 0.0, maxWidth, maxHeight, 0.0, maxHeight);
//
//        Mat M = Imgproc.getPerspectiveTransform(srcMat, dstMat);
//        Imgproc.warpPerspective(src, resultMat, M, resultMat.size());
//
//        srcMat.release();
//        dstMat.release();
//        M.release();
//
//        return resultMat;
}







