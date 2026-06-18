// tests/helpers/helpers.js
// Shared helper utilities for TutorNow Appium test suite

const CREDENTIALS = {
  student: { email: 'student@tutornow.com', password: 'student123' },
  tutor:   { email: 'tutor@tutornow.com',   password: 'tutor123'   },
  admin:   { email: 'admin@tutornow.com',    password: 'admin123'   },
};

/**
 * Wait for an element to be visible, then return it.
 */
async function waitForElement(selector, timeout = 10000) {
  const el = await $(selector);
  await el.waitForDisplayed({ timeout });
  return el;
}

/**
 * Wait for element by resource-id
 */
async function byId(resourceId, timeout = 10000) {
  return waitForElement(`android=new UiSelector().resourceId("com.tutornow.app:id/${resourceId}")`, timeout);
}

/**
 * Wait for element by text
 */
async function byText(text, timeout = 10000) {
  return waitForElement(`android=new UiSelector().text("${text}")`, timeout);
}

/**
 * Wait for element containing text
 */
async function byContainsText(text, timeout = 10000) {
  return waitForElement(`android=new UiSelector().textContains("${text}")`, timeout);
}

/**
 * Login helper - logs in with given role
 */
async function loginAs(role) {
  const creds = CREDENTIALS[role];
  const emailField = await byId('etEmail');
  await emailField.clearValue();
  await emailField.setValue(creds.email);

  const passwordField = await byId('etPassword');
  await passwordField.clearValue();
  await passwordField.setValue(creds.password);

  const loginBtn = await byId('btnLogin');
  await loginBtn.click();
  await driver.pause(2000);
}

/**
 * Navigate back from any screen
 */
async function pressBack() {
  await driver.back();
  await driver.pause(500);
}

/**
 * Logout helper
 */
async function logout() {
  try {
    const profileTab = await byId('navigation_profile');
    await profileTab.click();
    await driver.pause(1000);
    const logoutBtn = await byId('btnLogout');
    await logoutBtn.click();
    await driver.pause(1500);
  } catch (e) {
    // Force restart the app
    await driver.terminateApp('com.tutornow.app');
    await driver.activateApp('com.tutornow.app');
    await driver.pause(3000);
  }
}

/**
 * Scroll down once
 */
async function scrollDown() {
  const { width, height } = await driver.getWindowSize();
  await driver.action('pointer')
    .move({ duration: 0, x: width / 2, y: height * 0.7 })
    .down({ button: 0 })
    .move({ duration: 800, x: width / 2, y: height * 0.3 })
    .up({ button: 0 })
    .perform();
  await driver.pause(500);
}

/**
 * Get visible text of an element by resource ID
 */
async function getText(resourceId) {
  const el = await byId(resourceId);
  return el.getText();
}

/**
 * Check if element exists (non-throwing)
 */
async function elementExists(selector, timeout = 3000) {
  try {
    const el = await $(selector);
    await el.waitForDisplayed({ timeout });
    return true;
  } catch {
    return false;
  }
}

module.exports = {
  CREDENTIALS,
  waitForElement,
  byId,
  byText,
  byContainsText,
  loginAs,
  pressBack,
  logout,
  scrollDown,
  getText,
  elementExists,
};
