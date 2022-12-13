// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Ports;
import frc.robot.Constants.DriveConstants;
import frc.robot.sensors.RomiGyro;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
  // The only instance of drivetrain
  private static Drivetrain instance;

  // Drivetrain actuators
  private final Spark leftMotor;
  private final Spark rightMotor;

  // Abstracted drive object
  private final DifferentialDrive diffDrive;

  // Sensors
  private final Encoder leftEncoder;
  private final Encoder rightEncoder;
  private final RomiGyro gyro;
  private final BuiltInAccelerometer accel;

  /** Creates a new Drivetrain */
  private Drivetrain() {
    // These motors move drivetrain
    leftMotor = new Spark(Ports.LEFT_DRIVE_MOTOR);
    rightMotor = new Spark(Ports.RIGHT_DRIVE_MOTOR);

    // Inverts one side of the motors
    leftMotor.setInverted(!DriveConstants.DRIVE_INVERTED);
    rightMotor.setInverted(DriveConstants.DRIVE_INVERTED);

    // Differential drive object provides driving methods
    diffDrive = new DifferentialDrive(leftMotor, rightMotor);

    // Encoders provide number of motor spins
    leftEncoder = new Encoder(Ports.LEFT_DRIVE_ENCODER_A, Ports.LEFT_DRIVE_ENCODER_B);
    rightEncoder = new Encoder(Ports.RIGHT_DRIVE_ENCODER_A, Ports.RIGHT_DRIVE_ENCODER_B);

    // Use inches as unit for encoder distances
    leftEncoder.setDistancePerPulse((Math.PI * DriveConstants.WHEEL_DIAMETER_INCH) / DriveConstants.COUNTS_PER_REV);
    rightEncoder.setDistancePerPulse((Math.PI * DriveConstants.WHEEL_DIAMETER_INCH) / DriveConstants.COUNTS_PER_REV);

    // Resets encoder readings to zero
    resetEncoders();

    // Gyro provides angle changes
    gyro = new RomiGyro();

    // Resets gyro readings to zero
    resetGyro();

    // Provides acceleration
    accel = new BuiltInAccelerometer();
  }

  /** Provides the only instance of the drivetrain **/
  public static Drivetrain getInstance() {
    if(instance == null) {
      return new Drivetrain();
    }
    return instance;
  }

  /** Drive style that only uses one joystick
   * @param xaxisSpeed Linear speed
   * @param zaxisRotate Rotational speed
   */
  public void arcadeDrive(double xaxisSpeed, double zaxisRotate) {
    diffDrive.arcadeDrive(xaxisSpeed, zaxisRotate);
  }

  /** Resets the drivetrain encoders */
  public void resetEncoders() {
    leftEncoder.reset();
    rightEncoder.reset();
  }

  /** Gets raw counts of the left encoders */
  public int getLeftEncoderCount() {
    return leftEncoder.get();
  }

  /** Gets raw counts of the right encoders */
  public int getRightEncoderCount() {
    return rightEncoder.get();
  }

  /** Gets the distance driven by the left wheels in inches */
  public double getLeftDistanceInch() {
    return leftEncoder.getDistance();
  }

  /** Gets the distance driven by the right wheels in inches */
  public double getRightDistanceInch() {
    return rightEncoder.getDistance();
  }

  /** Gets the average distance driven by both the wheels */
  public double getAverageDistanceInch() {
    return (getLeftDistanceInch() + getRightDistanceInch()) / 2.0;
  }

  /**
   * The acceleration in the X-axis
   * @return The acceleration of the Romi along the X-axis in Gs
   */
  public double getAccelX() {
    return accel.getX();
  }

  /**
   * The acceleration in the Y-axis
   * @return The acceleration of the Romi along the Y-axis in Gs
   */
  public double getAccelY() {
    return accel.getY();
  }

  /**
   * The acceleration in the Z-axis
   * @return The acceleration of the Romi along the Z-axis in Gs
   */
  public double getAccelZ() {
    return accel.getZ();
  }

  /**
   * Current angle of the Romi around the X-axis
   * @return The current angle of the Romi in degrees
   */
  public double getGyroAngleX() {
    return gyro.getAngleX();
  }

  /**
   * Current angle of the Romi around the Y-axis
   * @return The current angle of the Romi in degrees
   */
  public double getGyroAngleY() {
    return gyro.getAngleY();
  }

  /**
   * Current angle of the Romi around the Z-axis
   * @return The current angle of the Romi in degrees
   */
  public double getGyroAngleZ() {
    return gyro.getAngleZ();
  }

  /** Reset the gyro. */
  public void resetGyro() {
    gyro.reset();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
