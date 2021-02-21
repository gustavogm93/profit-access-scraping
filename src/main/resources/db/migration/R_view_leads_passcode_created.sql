CREATE OR REPLACE VIEW view_leads_passcode_created AS
Select * from view_leads where max_reach_level = 6 ;


