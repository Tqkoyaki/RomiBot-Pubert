package frc.robot;

public final class Ports {
    // Drive
    public static final int LEFT_DRIVE_MOTOR = 0;
    public static final int RIGHT_DRIVE_MOTOR = 1;

    // Drive encoder ports
    public static final int LEFT_DRIVE_ENCODER_A = 4;
    public static final int LEFT_DRIVE_ENCODER_B = 5;
    public static final int RIGHT_DRIVE_ENCODER_A = 6;
    public static final int RIGHT_DRIVE_ENCODER_B = 7;

    // DIO ports for IO
    public static final int BUTTON_A = 0;
    public static final int GREEN_LED = 1; // Overides buttonB
    public static final int RED_LED = 2; // Overides buttonC
    public static final int YELLOW_LED = 3;

    public static final String PHOTON_CAMERA = "photonvision";
}