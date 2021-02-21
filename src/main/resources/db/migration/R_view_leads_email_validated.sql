CREATE OR REPLACE VIEW view_leads_email_validated AS
Select * from view_leads where max_reach_level = 2;

