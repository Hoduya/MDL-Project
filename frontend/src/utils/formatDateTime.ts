export default function formatDateTime(dateString: string) {
  const options: Intl.DateTimeFormatOptions = {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    hour12: true,
  };

  const formattedDate = new Intl.DateTimeFormat('en-US', options).format(new Date(dateString));
  return formattedDate.replace(/(\d+)\/(\d+)\/(\d+), (\d+):(\d+) (AM|PM)/, '$3.$1.$2 오후 $4:$5');
}