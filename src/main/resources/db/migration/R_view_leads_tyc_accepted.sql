CREATE OR REPLACE VIEW view_leads_tyc_accepted AS
Select * from view_leads where max_reach_level = 5 ;


