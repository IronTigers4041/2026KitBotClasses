// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.NeutralModeValue;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.FuelConstants.*;

public class CANFuelSubsystem extends SubsystemBase {
  private final TalonFX feederRoller;
  private final TalonFX intakeLauncherRoller;
  private static TalonFXConfiguration funnelTalonConfiguration;
  private static CurrentLimitsConfigs currentLimitConfigs;

  /** Creates a new CANBallSubsystem. */
  public CANFuelSubsystem() {

    funnelTalonConfiguration = new TalonFXConfiguration();
    currentLimitConfigs = funnelTalonConfiguration.CurrentLimits;

    currentLimitConfigs.SupplyCurrentLowerLimit = 5;
    currentLimitConfigs.SupplyCurrentLimit = 40;
    currentLimitConfigs.SupplyCurrentLowerTime = 1.0;
    currentLimitConfigs.SupplyCurrentLimitEnable = false;

    currentLimitConfigs.StatorCurrentLimit = 80;
    currentLimitConfigs.StatorCurrentLimitEnable = false;
    
    feederRoller = new TalonFX(FEEDER_MOTOR_ID);
    feederRoller.getConfigurator().apply(funnelTalonConfiguration);
    feederRoller.setNeutralMode(NeutralModeValue.Coast);

    intakeLauncherRoller = new TalonFX(INTAKE_LAUNCHER_MOTOR_ID);
    intakeLauncherRoller.getConfigurator().apply(funnelTalonConfiguration);
    intakeLauncherRoller.setNeutralMode(NeutralModeValue.Coast);

    // put default values for various fuel operations onto the dashboard
    // all commands using this subsystem pull values from the dashbaord to allow
    // you to tune the values easily, and then replace the values in Constants.java
    // with your new values. For more information, see the Software Guide.
    SmartDashboard.putNumber("Intaking feeder roller value", INTAKING_FEEDER_VOLTAGE);
    SmartDashboard.putNumber("Intaking intake roller value", INTAKING_INTAKE_VOLTAGE);
    SmartDashboard.putNumber("Launching feeder roller value", LAUNCHING_FEEDER_VOLTAGE);
    SmartDashboard.putNumber("Launching launcher roller value", LAUNCHING_LAUNCHER_VOLTAGE);
    SmartDashboard.putNumber("Spin-up feeder roller value", SPIN_UP_FEEDER_VOLTAGE);
  }

  // A method to set the voltage of the intake roller
  public void setIntakeLauncherRoller(double voltage) {
    intakeLauncherRoller.setVoltage(voltage);
  }

  // A method to set the voltage of the intake roller
  public void setFeederRoller(double voltage) {
    feederRoller.setVoltage(voltage);
  }

  // A method to stop the rollers
  public void stop() {
    feederRoller.set(0);
    intakeLauncherRoller.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
