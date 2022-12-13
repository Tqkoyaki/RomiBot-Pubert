// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Ports;
import frc.robot.Constants.VisionConstants;

public class Vision extends SubsystemBase {
  // The only instance of primary vision system
  private Vision instance;

  // Camera object with pipeline
  private PhotonCamera camera;

  // Holds most recent vision data
  private PhotonPipelineResult rawData;

  /** Creates a new Vision. */
  private Vision() {
    camera = new PhotonCamera(Ports.PHOTON_CAMERA);
    rawData = camera.getLatestResult();
  }

  /** Provides the only instance of vision */
  public Vision getInstance() {
    if (instance == null) {
      instance = new Vision();
    }
    return instance;
  }

  /** Checks if there is a target */
  public boolean hasTarget() {
    return rawData.hasTargets();
  }

  /** Gets the yaw of the best target in degrees */
  public double getBestYaw() {
    try {
      return rawData.getBestTarget().getYaw();
    } catch (Exception e) {
      DriverStation.reportWarning("Yaw found no target", false);
      return 0;
    }
  }

  /** Gets the pitch of the best target in degrees */
  public double getBestPitch() {
    try {
      return rawData.getBestTarget().getPitch();
    } catch (Exception e) {
      DriverStation.reportWarning("Pitch found no target", false);
      return 0;
    }
  }

  /** Gets the area of the best target as percent */
  public double getBestArea() {
    try {
      return rawData.getBestTarget().getArea();
    } catch (Exception e) {
      DriverStation.reportWarning("Area found no target", false);
      return -1;
    }
  }

  /** Gets the best camera to target position. 
   * Best means having the lowest projection error */
  public Transform3d getCameraToBestTarget() {
    try {
      return rawData.getBestTarget().getBestCameraToTarget();
    } catch (Exception e) {
      DriverStation.reportWarning("Camera to best target found no target", false);
      return new Transform3d();
    }
  }

  /** Gets the camera to target position with the highest projection error */
  public Transform3d getCameraToWorstTarget() {
    try {
      return rawData.getBestTarget().getAlternateCameraToTarget();
    } catch (Exception e) {
      DriverStation.reportWarning("Camera to worst target found no target", false);
      return new Transform3d();
    }
  }

  /** Gets how ambiguous the pose of the best target is.
   * Higher is more ambiguous. Anything above 0.2 is likely ambiguous */
  public double getBestTargetAmbiguityValue() {
    try {
      return rawData.getBestTarget().getPoseAmbiguity();
    } catch (Exception e) {
      DriverStation.reportWarning("Best target ambiguity found no target", false);
      return -1;
    }
  }

  /** Gets if the best target is ambiguous */
  public boolean isBestTargetAmbiguous() {
    try {
      double amb = rawData.getBestTarget().getPoseAmbiguity();
      return amb < 0.2 && amb != -1;
    } catch (Exception e) {
      DriverStation.reportWarning("Best target ambiguous found no target", false);
      return false;
    }
  }

  /** Capture the pre-processed image for debugging */
  public void capturePreprocessedImage() {
    camera.takeInputSnapshot();
  }

  /** Capture the post-processed image for debugging */
  public void capturePostprocessedImage() {
    camera.takeOutputSnapshot();
  }

  /** Get distance from the camera */
  public double getDistanceFromTarget() {
    try {
      return PhotonUtils.calculateDistanceToTargetMeters(
        VisionConstants.CAMERA_HEIGHT_METERS,
        VisionConstants.TARGET_HEIGHT_METERS,
        VisionConstants.CAMERA_PITCH_RADIANS,
        Units.degreesToRadians(getBestPitch()));
    } catch (Exception e) {
      DriverStation.reportWarning("Distance from target found no target", false);
      return -1;
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // Updates the vision data (gets everything at once for lower latency)
    rawData = camera.getLatestResult();
  }
}
