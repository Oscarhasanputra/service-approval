DROP TABLE IF EXISTS "public"."trc_configapprovalsubeventassignee";
CREATE TABLE "public"."trc_configapprovalsubeventassignee" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "assignee_attribute" varchar(255) COLLATE "pg_catalog"."default",
  "assignee_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "assignee_type" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "level" int4 NOT NULL,
  "total_approval_needed" int4 NOT NULL,
  "trc_configapprovalsubevent_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."trc_configapprovalsubeventassignee" OWNER TO "postgres";

-- ----------------------------
-- Primary Key structure for table trc_configapprovalsubeventassignee
-- ----------------------------
ALTER TABLE "public"."trc_configapprovalsubeventassignee" ADD CONSTRAINT "trc_configapprovalsubeventassignee_pkey" PRIMARY KEY ("id");
