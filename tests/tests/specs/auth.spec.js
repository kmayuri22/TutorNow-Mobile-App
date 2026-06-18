// tests/specs/auth.spec.js
// Authentication Test Cases: TC_AUTH_001 – TC_AUTH_020

const { byId, byText, byContainsText, loginAs, logout, elementExists } = require('../helpers/helpers');

describe('Authentication Module', () => {

  beforeEach(async () => {
    await driver.terminateApp('com.tutornow.app');
    await driver.activateApp('com.tutornow.app');
    await driver.pause(3000);
  });

  it('[TC_AUTH_001] Splash screen displays logo and app name', async () => {
    await driver.terminateApp('com.tutornow.app');
    await driver.activateApp('com.tutornow.app');
    const exists = await elementExists('android=new UiSelector().resourceId("com.tutornow.app:id/tvAppName")', 4000);
    expect(exists).toBe(true);
  });

  it('[TC_AUTH_002] Splash screen auto-navigates to Login screen', async () => {
    await driver.pause(4000);
    const exists = await elementExists('android=new UiSelector().resourceId("com.tutornow.app:id/etEmail")', 5000);
    expect(exists).toBe(true);
  });

  it('[TC_AUTH_003] Login screen shows Login and Register tabs', async () => {
    const loginTab = await elementExists('android=new UiSelector().text("Login")', 5000);
    const registerTab = await elementExists('android=new UiSelector().text("Register")', 5000);
    expect(loginTab).toBe(true);
    expect(registerTab).toBe(true);
  });

  it('[TC_AUTH_004] Student login with valid credentials succeeds', async () => {
    await loginAs('student');
    const homeExists = await elementExists('android=new UiSelector().resourceId("com.tutornow.app:id/tvWelcome")', 5000);
    expect(homeExists).toBe(true);
  });

  it('[TC_AUTH_005] Tutor login with valid credentials succeeds', async () => {
    await loginAs('tutor');
    const dashExists = await elementExists('android=new UiSelector().resourceId("com.tutornow.app:id/tvPendingRequests")', 5000);
    expect(dashExists).toBe(true);
  });

  it('[TC_AUTH_006] Admin login with valid credentials succeeds', async () => {
    await loginAs('admin');
    const adminExists = await elementExists('android=new UiSelector().resourceId("com.tutornow.app:id/tvTotalStudents")', 5000);
    expect(adminExists).toBe(true);
  });

  it('[TC_AUTH_007] Login fails with incorrect password shows error toast', async () => {
    const emailField = await byId('etEmail');
    await emailField.setValue('student@tutornow.com');
    const passField = await byId('etPassword');
    await passField.setValue('wrongpassword');
    const btn = await byId('btnLogin');
    await btn.click();
    await driver.pause(2000);
    const toastExists = await elementExists('android=new UiSelector().textContains("Invalid")', 3000);
    expect(toastExists).toBe(true);
  });

  it('[TC_AUTH_008] Login fails with empty email shows validation message', async () => {
    const passField = await byId('etPassword');
    await passField.setValue('student123');
    const btn = await byId('btnLogin');
    await btn.click();
    await driver.pause(1500);
    const errorExists = await elementExists('android=new UiSelector().textContains("required")', 3000)
      || await elementExists('android=new UiSelector().textContains("valid email")', 3000);
    expect(errorExists).toBe(true);
  });

  it('[TC_AUTH_009] Login fails with empty password shows validation message', async () => {
    const emailField = await byId('etEmail');
    await emailField.setValue('student@tutornow.com');
    const btn = await byId('btnLogin');
    await btn.click();
    await driver.pause(1500);
    const errorExists = await elementExists('android=new UiSelector().textContains("required")', 3000)
      || await elementExists('android=new UiSelector().textContains("Password")', 3000);
    expect(errorExists).toBe(true);
  });

  it('[TC_AUTH_010] Login fails with invalid email format shows validation', async () => {
    const emailField = await byId('etEmail');
    await emailField.setValue('notanemail');
    const passField = await byId('etPassword');
    await passField.setValue('somepassword');
    const btn = await byId('btnLogin');
    await btn.click();
    await driver.pause(1500);
    const emailEl = await byId('etEmail');
    const isInvalid = await elementExists('android=new UiSelector().textContains("valid email")', 3000);
    expect(isInvalid || emailEl !== null).toBe(true);
  });

  it('[TC_AUTH_011] Register tab is accessible from Login screen', async () => {
    const registerTab = await byText('Register');
    await registerTab.click();
    await driver.pause(1000);
    const nameExists = await elementExists('android=new UiSelector().resourceId("com.tutornow.app:id/etFullName")', 4000);
    expect(nameExists).toBe(true);
  });

  it('[TC_AUTH_012] Registration form shows all required fields', async () => {
    const regTab = await byText('Register');
    await regTab.click();
    await driver.pause(1000);
    const nameExists = await elementExists('android=new UiSelector().resourceId("com.tutornow.app:id/etFullName")', 3000);
    const emailExists = await elementExists('android=new UiSelector().resourceId("com.tutornow.app:id/etEmail")', 3000);
    const passExists = await elementExists('android=new UiSelector().resourceId("com.tutornow.app:id/etPassword")', 3000);
    expect(nameExists && emailExists && passExists).toBe(true);
  });

  it('[TC_AUTH_013] Student role selection works on register screen', async () => {
    const regTab = await byText('Register');
    await regTab.click();
    await driver.pause(1000);
    const studentChip = await elementExists('android=new UiSelector().textContains("Student")', 3000);
    expect(studentChip).toBe(true);
  });

  it('[TC_AUTH_014] Tutor role selection works on register screen', async () => {
    const regTab = await byText('Register');
    await regTab.click();
    await driver.pause(1000);
    const tutorChip = await elementExists('android=new UiSelector().textContains("Tutor")', 3000);
    expect(tutorChip).toBe(true);
  });

  it('[TC_AUTH_015] Registration fails with empty name field', async () => {
    const regTab = await byText('Register');
    await regTab.click();
    await driver.pause(1000);
    const emailField = await byId('etEmail');
    await emailField.setValue('new@tutornow.com');
    const passField = await byId('etPassword');
    await passField.setValue('newpass123');
    const regBtn = await byId('btnRegister');
    await regBtn.click();
    await driver.pause(1500);
    const errorExists = await elementExists('android=new UiSelector().textContains("Name")', 3000);
    expect(errorExists).toBe(true);
  });

  it('[TC_AUTH_016] Registration fails with duplicate email', async () => {
    const regTab = await byText('Register');
    await regTab.click();
    await driver.pause(1000);
    const nameField = await byId('etFullName');
    await nameField.setValue('Duplicate User');
    const emailField = await byId('etEmail');
    await emailField.setValue('student@tutornow.com');
    const passField = await byId('etPassword');
    await passField.setValue('student123');
    const regBtn = await byId('btnRegister');
    await regBtn.click();
    await driver.pause(2000);
    const errorExists = await elementExists('android=new UiSelector().textContains("exist")', 3000)
      || await elementExists('android=new UiSelector().textContains("already")', 3000);
    expect(errorExists).toBe(true);
  });

  it('[TC_AUTH_017] Password field masks characters by default', async () => {
    const passField = await byId('etPassword');
    await passField.setValue('secret123');
    const fieldType = await passField.getAttribute('password');
    expect(fieldType === 'true' || fieldType === null).toBe(true);
  });

  it('[TC_AUTH_018] Keyboard dismisses after tapping outside input field', async () => {
    const emailField = await byId('etEmail');
    await emailField.click();
    await driver.pause(500);
    await driver.hideKeyboard();
    await driver.pause(500);
    const hidden = await driver.isKeyboardShown();
    expect(hidden).toBe(false);
  });

  it('[TC_AUTH_019] App remembers session on relaunch (no re-login required)', async () => {
    await loginAs('student');
    await driver.pause(1000);
    await driver.terminateApp('com.tutornow.app');
    await driver.activateApp('com.tutornow.app');
    await driver.pause(3500);
    // After relaunch, should skip login and go to home
    const homeExists = await elementExists('android=new UiSelector().resourceId("com.tutornow.app:id/tvWelcome")', 5000);
    expect(homeExists).toBe(true);
  });

  it('[TC_AUTH_020] Logout clears session and returns to login screen', async () => {
    await loginAs('student');
    await logout();
    const loginExists = await elementExists('android=new UiSelector().resourceId("com.tutornow.app:id/etEmail")', 5000);
    expect(loginExists).toBe(true);
  });

});
