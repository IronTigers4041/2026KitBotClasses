// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.FuelConstants.BOTTOM_FEEDER_MOTOR_ID;
import static frc.robot.Constants.FuelConstants.FEEDER_MOTOR_ID;
import static frc.robot.Constants.FuelConstants.INTAKE_LAUNCHER_MOTOR_ID;
import static frc.robot.Constants.FuelConstants.INTAKING_FEEDER_VOLTAGE;
import static frc.robot.Constants.FuelConstants.INTAKING_INTAKE_VOLTAGE;
import static frc.robot.Constants.FuelConstants.LAUNCHING_FEEDER_VOLTAGE;
import static frc.robot.Constants.FuelConstants.LAUNCHING_LAUNCHER_VOLTAGE;
import static frc.robot.Constants.FuelConstants.SPIN_UP_FEEDER_VOLTAGE;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CANFuelSubsystem extends SubsystemBase {
  private final TalonFX feederRoller;
  private final TalonFX bottom_feederRoller;
  private static TalonFXConfiguration feederTalonConfiguration;
  private static CurrentLimitsConfigs currentLimitConfigs;
  private static TalonFXConfiguration bottom_feederTalonConfiguration;
  private static CurrentLimitsConfigs bottom_currentLimitConfigs;

  private SparkFlex intakeLauncherRoller;
  private SparkMaxConfig m_motorConfig;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
  private SparkFlex bottom_intakeLauncherRoller;
  /** Creates a new CANBallSubsystem. */
  public CANFuelSubsystem() {

    feederTalonConfiguration = new TalonFXConfiguration();
    currentLimitConfigs = feederTalonConfiguration.CurrentLimits;

    currentLimitConfigs.SupplyCurrentLowerLimit = 5;
    currentLimitConfigs.SupplyCurrentLimit = 40;
    currentLimitConfigs.SupplyCurrentLowerTime = 1.0;
    currentLimitConfigs.SupplyCurrentLimitEnable = false;

    currentLimitConfigs.StatorCurrentLimit = 80;
    currentLimitConfigs.StatorCurrentLimitEnable = false;

    feederTalonConfiguration.CurrentLimits = currentLimitConfigs;

     bottom_feederTalonConfiguration = new TalonFXConfiguration();
    bottom_currentLimitConfigs = bottom_feederTalonConfiguration.CurrentLimits;

    bottom_currentLimitConfigs.SupplyCurrentLowerLimit = 5;
    bottom_currentLimitConfigs.SupplyCurrentLimit = 40;
    bottom_currentLimitConfigs.SupplyCurrentLowerTime = 1.0;
    bottom_currentLimitConfigs.SupplyCurrentLimitEnable = false;

    bottom_currentLimitConfigs.StatorCurrentLimit = 80;
    bottom_currentLimitConfigs.StatorCurrentLimitEnable = false;

    feederTalonConfiguration.CurrentLimits = currentLimitConfigs;

    feederRoller = new TalonFX(FEEDER_MOTOR_ID);
    feederRoller.getConfigurator().apply(feederTalonConfiguration);
    feederRoller.setNeutralMode(NeutralModeValue.Coast);
    bottom_feederRoller = new TalonFX(BOTTOM_FEEDER_MOTOR_ID);
    bottom_feederRoller.getConfigurator().apply(feederTalonConfiguration);
    bottom_feederRoller.setNeutralMode(NeutralModeValue.Coast);

    intakeLauncherRoller = new SparkFlex(INTAKE_LAUNCHER_MOTOR_ID, MotorType.kBrushless);
    m_motorConfig = new SparkMaxConfig();

   m_motorConfig = new SparkMaxConfig();
    m_motorConfig.voltageCompensation(12);
    m_motorConfig.smartCurrentLimit(100,120,20);
    m_motorConfig.inverted(true);
    m_motorConfig.idleMode(IdleMode.kCoast);
    intakeLauncherRoller.configure(m_motorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);


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
    bottom_feederRoller.set(voltage);
  }

  // A method to set the voltage of the intake roller
  public void setFeederRoller(double voltage) {
    feederRoller.setVoltage(voltage);
    bottom_feederRoller.setVoltage(voltage);
  }

  // A method to stop the rollers
  public void stop() {
    feederRoller.set(0);
    bottom_feederRoller.set(0);
    intakeLauncherRoller.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
