DROP TABLE IF EXISTS "public"."trx_approvalhistory";
CREATE TABLE "public"."trx_approvalhistory" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "created_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "created_date" timestamp(6) NOT NULL,
  "modified_by" varchar(255) COLLATE "pg_catalog"."default",
  "modified_date" timestamp(6),
  "branch" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "date" timestamp(6) NOT NULL,
  "deleted_reason" varchar(255) COLLATE "pg_catalog"."default",
  "endpoint_on_finished" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "expired_date" timestamp(6),
  "frontend_view" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "is_deleted" bool NOT NULL,
  "remarks" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "status" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "stop_when_rejected" bool NOT NULL,
  "trx_configapproval_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."trx_approvalhistory" OWNER TO "postgres";

-- ----------------------------
-- Primary Key structure for table trx_approvalhistory
-- ----------------------------
ALTER TABLE "public"."trx_approvalhistory" ADD CONSTRAINT "trx_approvalhistory_pkey" PRIMARY KEY ("id");
