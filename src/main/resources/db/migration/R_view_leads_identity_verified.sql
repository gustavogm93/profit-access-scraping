CREATE OR REPLACE VIEW view_leads_identity_verified AS
Select * from view_leads where max_reach_level = 3;


