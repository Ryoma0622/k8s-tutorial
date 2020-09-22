#!/bin/bash

websocat $(curl -sS -X GET -H "Authorization: Bearer ${SLACK_BOT_TOKEN}" https://slack.com/api/rtm.connect | jq -r '.url')
