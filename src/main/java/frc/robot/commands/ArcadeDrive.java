// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.function.Supplier;

public class ArcadeDrive extends CommandBase {
  // Subsystem needed for command
  private final Drivetrain drivetrain;

  // Gamepad inputs are supplied here
  private final Supplier<Double> linSupplier;
  private final Supplier<Double> rotSupplier;

  /**
   * The arcade drive command allows for driving using linear and rotational speed
   * @param linearSupplier Lambda supplier of forward/backward speed
   * @param rotationalSupplier Lambda supplier of rotational speed
   */
  public ArcadeDrive(Supplier<Double> linearSupplier, Supplier<Double> rotationalSupplier) {
    drivetrain = Drivetrain.getInstance();
    linSupplier = linearSupplier;
    rotSupplier = rotationalSupplier;

    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Stops the motors for a clean start
    drivetrain.arcadeDrive(0, 0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Drives the robot
    drivetrain.arcadeDrive(linSupplier.get(), rotSupplier.get());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Stops the motors for safety reasons
    drivetrain.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // This command should never end
    return false;
  }
}
