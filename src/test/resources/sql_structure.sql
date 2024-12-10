/*
 Navicat Premium Data Transfer

 Source Server         : b2b-microservices-new
 Source Server Type    : PostgreSQL
 Source Server Version : 140012 (140012)
 Source Host           : b2b-sit.cc2jtqqitlmv.ap-southeast-1.rds.amazonaws.com:5432
 Source Catalog        : BIT_SERVICE_APPROVAL_SIT
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 140012 (140012)
 File Encoding         : 65001

 Date: 28/11/2024 15:27:57
*/


-- ----------------------------
-- Table structure for ms_eventapproval
-- ----------------------------
DROP TABLE IF EXISTS "public"."ms_eventapproval";
CREATE TABLE "public"."ms_eventapproval" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "created_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "created_date" timestamp(6) NOT NULL,
  "modified_by" varchar(255) COLLATE "pg_catalog"."default",
  "modified_date" timestamp(6),
  "code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "deleted_reason" varchar(255) COLLATE "pg_catalog"."default",
  "is_active" bool NOT NULL,
  "is_deleted" bool NOT NULL,
  "ms_application_code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "ms_application_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "remarks" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."ms_eventapproval" OWNER TO "postgres";

-- ----------------------------
-- Table structure for trc_approvalhistorylevel
-- ----------------------------
DROP TABLE IF EXISTS "public"."trc_approvalhistorylevel";
CREATE TABLE "public"."trc_approvalhistorylevel" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "approval_date" timestamp(6),
  "approval_note" varchar(255) COLLATE "pg_catalog"."default",
  "approval_status" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "level" int4 NOT NULL,
  "send_notification_date" timestamp(6),
  "total_approval_needed" int4 NOT NULL,
  "trx_approvalhistory_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."trc_approvalhistorylevel" OWNER TO "postgres";

-- ----------------------------
-- Table structure for trc_approvalrequestlevelassignee
-- ----------------------------
DROP TABLE IF EXISTS "public"."trc_approvalrequestlevelassignee";
CREATE TABLE "public"."trc_approvalrequestlevelassignee" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "approval_date" timestamp(6),
  "approval_note" varchar(255) COLLATE "pg_catalog"."default",
  "approval_status" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "assignee_email" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "assignee_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "assignee_name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "assignee_telegram" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "assignee_whatsapp" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "send_email_notification" bool NOT NULL,
  "send_telegram_notification" bool NOT NULL,
  "send_wa_notification" bool NOT NULL,
  "trc_approvalhistorylevel_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."trc_approvalrequestlevelassignee" OWNER TO "postgres";

-- ----------------------------
-- Table structure for trc_configapprovalsubevent
-- ----------------------------
DROP TABLE IF EXISTS "public"."trc_configapprovalsubevent";
CREATE TABLE "public"."trc_configapprovalsubevent" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "auto_approved" bool NOT NULL,
  "code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "remarks" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "trx_configapproval_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "type" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."trc_configapprovalsubevent" OWNER TO "postgres";

-- ----------------------------
-- Table structure for trc_configapprovalsubeventassignee
-- ----------------------------
DROP TABLE IF EXISTS "public"."trc_configapprovalsubeventassignee";
CREATE TABLE "public"."trc_configapprovalsubeventassignee" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "assignee_code" varchar(255) COLLATE "pg_catalog"."default",
  "assignee_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "assignee_type" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "level" int4 NOT NULL,
  "total_approval_needed" int4 NOT NULL,
  "trc_configapprovalsubevent_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."trc_configapprovalsubeventassignee" OWNER TO "postgres";

-- ----------------------------
-- Table structure for trc_configapprovalsubeventwhitelistuser
-- ----------------------------
DROP TABLE IF EXISTS "public"."trc_configapprovalsubeventwhitelistuser";
CREATE TABLE "public"."trc_configapprovalsubeventwhitelistuser" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "trc_configapprovalsubevent_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "whitelistuser_code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "whitelistuser_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "whitelistuser_type" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."trc_configapprovalsubeventwhitelistuser" OWNER TO "postgres";

-- ----------------------------
-- Table structure for trx_approvalrequest
-- ----------------------------
DROP TABLE IF EXISTS "public"."trx_approvalrequest";
CREATE TABLE "public"."trx_approvalrequest" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "created_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "created_date" timestamp(6) NOT NULL,
  "modified_by" varchar(255) COLLATE "pg_catalog"."default",
  "modified_date" timestamp(6),
  "code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "date" timestamp(6) NOT NULL,
  "deleted_reason" varchar(255) COLLATE "pg_catalog"."default",
  "endpoint_on_finished" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "expired_date" timestamp(6),
  "frontend_view" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "is_deleted" bool NOT NULL,
  "ms_branch_code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "ms_branch_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "remarks" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "status" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "stop_when_rejected" bool NOT NULL,
  "trx_configapproval_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."trx_approvalrequest" OWNER TO "postgres";

-- ----------------------------
-- Table structure for trx_configapproval
-- ----------------------------
DROP TABLE IF EXISTS "public"."trx_configapproval";
CREATE TABLE "public"."trx_configapproval" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "created_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "created_date" timestamp(6) NOT NULL,
  "modified_by" varchar(255) COLLATE "pg_catalog"."default",
  "modified_date" timestamp(6),
  "auto_send_request" bool NOT NULL,
  "deleted_reason" varchar(255) COLLATE "pg_catalog"."default",
  "effective_date" timestamp(6) NOT NULL,
  "endpoint_on_finished" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "frontend_view" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "is_deleted" bool NOT NULL,
  "ms_branch_code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "ms_branch_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "ms_eventapproval_code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "ms_eventapproval_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "remarks" varchar(255) COLLATE "pg_catalog"."default",
  "stop_when_rejected" bool NOT NULL
)
;
ALTER TABLE "public"."trx_configapproval" OWNER TO "postgres";

-- ----------------------------
-- Primary Key structure for table ms_eventapproval
-- ----------------------------
ALTER TABLE "public"."ms_eventapproval" ADD CONSTRAINT "ms_eventapproval_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table trc_approvalhistorylevel
-- ----------------------------
ALTER TABLE "public"."trc_approvalhistorylevel" ADD CONSTRAINT "trc_approvalhistorylevel_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table trc_approvalrequestlevelassignee
-- ----------------------------
ALTER TABLE "public"."trc_approvalrequestlevelassignee" ADD CONSTRAINT "trc_approvalrequestlevelassignee_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Checks structure for table trc_configapprovalsubevent
-- ----------------------------
ALTER TABLE "public"."trc_configapprovalsubevent" ADD CONSTRAINT "trc_configapprovalsubevent_type_check" CHECK (type::text = ANY (ARRAY['EVENT'::character varying, 'SUBEVENT'::character varying]::text[]));

-- ----------------------------
-- Primary Key structure for table trc_configapprovalsubevent
-- ----------------------------
ALTER TABLE "public"."trc_configapprovalsubevent" ADD CONSTRAINT "trc_configapprovalsubevent_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table trc_configapprovalsubeventassignee
-- ----------------------------
ALTER TABLE "public"."trc_configapprovalsubeventassignee" ADD CONSTRAINT "trc_configapprovalsubeventassignee_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Checks structure for table trc_configapprovalsubeventwhitelistuser
-- ----------------------------
ALTER TABLE "public"."trc_configapprovalsubeventwhitelistuser" ADD CONSTRAINT "trc_configapprovalsubeventwhitelistuse_whitelistuser_type_check" CHECK (whitelistuser_type::text = ANY (ARRAY['USER_INTERNAL'::character varying, 'HEAD_POSITION'::character varying, 'HEAD_POSITION_ALLOCATION'::character varying]::text[]));

-- ----------------------------
-- Primary Key structure for table trc_configapprovalsubeventwhitelistuser
-- ----------------------------
ALTER TABLE "public"."trc_configapprovalsubeventwhitelistuser" ADD CONSTRAINT "trc_configapprovalsubeventwhitelistuser_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table trx_approvalrequest
-- ----------------------------
ALTER TABLE "public"."trx_approvalrequest" ADD CONSTRAINT "trx_approvalrequest_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table trx_configapproval
-- ----------------------------
ALTER TABLE "public"."trx_configapproval" ADD CONSTRAINT "trx_configapproval_pkey" PRIMARY KEY ("id");
