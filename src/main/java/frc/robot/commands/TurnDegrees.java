// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TurnDegrees extends CommandBase {
  private final Drivetrain drivetrain;
  private final double degrees;
  private final double speed;

  /**
   * Creates a new TurnDegrees. This command will turn your robot for a desired rotation (in
   * degrees) and rotational speed.
   *
   * @param speed The speed which the robot will drive. Negative is in reverse.
   * @param degrees Degrees to turn. Leverages encoders to compare distance.
   * @param drive The drive subsystem on which this command will run
   */
  public TurnDegrees(double speed, double degrees) {
    drivetrain = Drivetrain.getInstance();
    this.degrees = degrees;
    this.speed = speed;

    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Resets the drivetrain and sensors for a clean slate
    drivetrain.arcadeDrive(0, 0);
    drivetrain.resetEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Only moves rotationally
    drivetrain.arcadeDrive(0, speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Resets the motors for a safe end
    drivetrain.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    /* Need to convert distance travelled to degrees. The Standard
       Romi Chassis found here, https://www.pololu.com/category/203/romi-chassis-kits,
       has a wheel placement diameter (149 mm) - width of the wheel (8 mm) = 141 mm
       or 5.551 inches. We then take into consideration the width of the tires.
    */
    double inchPerDegree = Math.PI * 5.551 / 360;
    // Compare distance travelled from start to distance based on degree turn
    return getAverageTurningDistance() >= (inchPerDegree * degrees);
  }

  private double getAverageTurningDistance() {
    double leftDistance = Math.abs(drivetrain.getLeftDistanceInch());
    double rightDistance = Math.abs(drivetrain.getRightDistanceInch());
    return (leftDistance + rightDistance) / 2.0;
  }
}
