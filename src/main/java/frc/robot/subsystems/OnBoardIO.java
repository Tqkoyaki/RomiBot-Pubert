// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Ports;

/**
 * This class represents the onboard IO of the Romi reference robot. This includes the pushbuttons
 * and LEDs.
 *
 * <p>DIO 0 - Button A (input only) DIO 1 - Button B (input) or Green LED (output) DIO 2 - Button C
 * (input) or Red LED (output) DIO 3 - Yellow LED (output only)
 */
public class OnBoardIO extends SubsystemBase {
  // The only instance of OnBoardIO
  private static OnBoardIO instance;

  // All IO on romi (excludes buttons b and c that use same DIO as LEDs)
  private final DigitalInput buttonA;
  private final DigitalOutput greenLED;
  private final DigitalOutput redLED;
  private final DigitalOutput yellowLED;

  /** Creates a new OnBoardIO */
  private OnBoardIO() {
    buttonA = new DigitalInput(Ports.BUTTON_A);
    greenLED = new DigitalOutput(Ports.GREEN_LED);
    redLED = new DigitalOutput(Ports.RED_LED);
    yellowLED = new DigitalOutput(Ports.YELLOW_LED);
  }

  /** Provides the only instance of the OnBoardIO **/
  public static OnBoardIO getInstance() {
    if(instance == null) {
      return new OnBoardIO();
    }
    return instance;
  }

  /** Gets if the A button is pressed */
  public boolean getButtonAPressed() {
    return buttonA.get();
  }

  /** Sets the green LED */
  public void setGreenLED(boolean value) {
    greenLED.set(value);
  }

  /** Sets the red LED */
  public void setRedLED(boolean value) {
    redLED.set(value);
  }

  /** Sets the yellow LED */
  public void setYellowLED(boolean value) {
    yellowLED.set(value);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
