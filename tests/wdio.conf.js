exports.config = {
  runner: 'local',
  port: 4723,

  specs: ['./tests/specs/**/*.spec.js'],
  exclude: [],
  maxInstances: 1,

  capabilities: [{
    platformName: 'Android',
    'appium:automationName': 'UiAutomator2',
    'appium:deviceName': 'Android Emulator',
    'appium:app': process.env.APK_PATH ||
      'C:\\Users\\venkataramana\\OneDrive\\Desktop\\Tutornow mobile app\\TutorNow\\app\\build\\outputs\\apk\\debug\\app-debug.apk',
    'appium:appPackage': 'com.tutornow.app',
    'appium:appActivity': 'com.tutornow.app.ui.splash.SplashActivity',
    'appium:newCommandTimeout': 240,
    'appium:noReset': false,
    'appium:fullReset': false,
    'appium:autoGrantPermissions': true,
  }],

  logLevel: 'info',
  bail: 0,
  waitforTimeout: 10000,
  connectionRetryTimeout: 120000,
  connectionRetryCount: 3,

  services: [
    ['appium', {
      command: 'appium',
      args: { relaxedSecurity: true }
    }]
  ],

  framework: 'mocha',
  reporters: [
    'spec',
    ['allure', {
      outputDir: 'allure-results',
      disableWebdriverStepsReporting: false,
      disableWebdriverScreenshotsReporting: false,
    }]
  ],

  mochaOpts: {
    ui: 'bdd',
    timeout: 120000,
    retries: 1,
  },

  // Global test result collector for report generation
  onComplete: function() {
    const { execSync } = require('child_process');
    try {
      execSync('python generate_report.py', { stdio: 'inherit' });
    } catch(e) {
      console.log('Report generation skipped (Python not available)');
    }
  },

  before: async function () {
    // Wait for splash screen to pass
    await driver.pause(3000);
  }
};
