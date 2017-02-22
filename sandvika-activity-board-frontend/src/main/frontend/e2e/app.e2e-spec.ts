import { ActivityFrontPage } from './app.po';

describe('activity-front App', function() {
  let page: ActivityFrontPage;

  beforeEach(() => {
    page = new ActivityFrontPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
